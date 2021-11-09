package com.example.softlogistica.ui.product

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.softlogistica.BaseActivity
import com.example.softlogistica.R
import com.example.softlogistica.database.ApplicationDatabase
import com.example.softlogistica.databinding.FragmentProductBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.fragment_product.*
import timber.log.Timber

class ProductFragment : Fragment() , SearchView.OnQueryTextListener {

    private lateinit var productViewModel: ProductViewModel
    private lateinit var binding: FragmentProductBinding
    private lateinit var searchView: SearchView
    private lateinit var productAdapter: ProductAdapter
    private lateinit var menuAdapter: MenuAdapter
    private lateinit var searchQuery: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        Timber.DebugTree()

    }

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View? {


        val sharedPrefs = requireContext().getSharedPreferences(R.string.preference_file_key.toString(), Context.MODE_PRIVATE)
        val all = sharedPrefs.all

        val application = requireNotNull(this.activity).application
        val dataSource =  ApplicationDatabase.getInstance(application).productDatabaseDao
        val productRepository =  ProductRepository(ApplicationDatabase.getInstance(application))
        val viewModelFactory = ProductViewModelFactory(dataSource, application, SavedStateHandle(), sharedPrefs, productRepository)


        binding =  FragmentProductBinding.inflate(inflater, container, false)
        productViewModel = ViewModelProvider(this, viewModelFactory).get(ProductViewModel::class.java)
        binding.viewmodel = productViewModel
        binding.lifecycleOwner = this


        recyclerViewProduct()
        bindingObservers()
        recyclerViewMenu()

        binding.floatingButtonNewProduct.setOnClickListener {
                productViewModel.onNewProductClicked()
        }

         requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner){
            if(recyclerview_product.isVisible){
                showMenuItems()
                searchView.setQuery("",false)
                searchView.onActionViewCollapsed()
                productViewModel.onSearchFiltered()
                hideRecyclerViewProduct()
                productViewModel.onMenuFiltered()
                productViewModel.onMenuFiltered()
            }else if(recyclerview_menu.isVisible){
                val intent = Intent(Intent.ACTION_MAIN)
                    .addCategory(Intent.CATEGORY_HOME)
                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
        }

        return binding.root
    }

    /**
     * Init recycler View Menu setting elements
     */
    private fun recyclerViewMenu() {
        menuAdapter = MenuAdapter(MenuListener { menu ->
            productViewModel.onProductMenuClicked(menu)
        })
        binding.recyclerviewMenu.adapter = menuAdapter
        binding.recyclerviewMenu.layoutManager =
            GridLayoutManager(activity, 2, GridLayoutManager.VERTICAL, false)
    }

    /**
     * Setting Listeners on a RecyclerView Product
     */
    private fun recyclerViewProduct() {
        productAdapter = ProductAdapter(ProductListener { product ->
            productViewModel.onProductClicked(product)
        }, DeleteClickListener { product ->
        productViewModel.onDeleteProductClicked(product)
        }, EditClickListener{ product ->
            productViewModel.onEditProductClicked(product)
        })

        binding.recyclerviewProduct.adapter = productAdapter
        binding.recyclerviewProduct.layoutManager = GridLayoutManager(activity, 1, GridLayoutManager.VERTICAL, false)
    }


    /**
     * Setting Observers of a ViewModel variables
     */
    private fun bindingObservers(){


        productViewModel.products.observe(viewLifecycleOwner, Observer { list ->
                list?.let {
                    productAdapter.submitList(it)
                    productAdapter.notifyDataSetChanged()
                }
        })

        productViewModel?.showMenuItems.observe(viewLifecycleOwner, Observer{ status ->
            if(status != null){
                showMenuItems()
            }
        })

        productViewModel?.hideMenuItems.observe(viewLifecycleOwner, Observer{ status ->
            if(status != null){
                hideMenuItems()
            }
        })


        binding.viewmodel?.showProgressBar?.observe(viewLifecycleOwner, Observer{ status ->
            if(status != null){
                showProgressBar()
                productViewModel.onProgressBarShowed()
            }
        })

        binding.viewmodel?.hideProgressBar?.observe(viewLifecycleOwner, Observer{ status ->
            if(status != null){
                hideProgressBar()
                productViewModel.onProgressBarHidden()
            }
        })

        binding.viewmodel?.showRecyclerViewProducts?.observe(viewLifecycleOwner, Observer { status ->
            if(status != null){
                showRecyclerViewProduct()
                productViewModel.onShowRecyclerViewProduct()
            }
        })

        binding.viewmodel?.hideRecyclerViewProducts?.observe(viewLifecycleOwner, Observer { status ->
            if(status != null){
                hideRecyclerViewProduct()
                productViewModel.onHideRecyclerViewProduct()
            }
        })

        productViewModel.navigateToNewProduct.observe(viewLifecycleOwner, Observer {
            if(it != null) {
                it.let {
                    this.findNavController().navigate(
                        ProductFragmentDirections.actionNavProductToFragmentNewProduct()
                    )
                    productViewModel.onNewProductNavigated()
                }
            }
        })



        productViewModel.refreshLayout.observe(viewLifecycleOwner, Observer { status ->
            if(status != null){
                status.let {
                    binding.swipeRefreshLayout.isRefreshing = false
                }
            }
        })


        productViewModel.failFetchProducts.observe(viewLifecycleOwner, Observer { message ->
            if(message != null){
                Toast.makeText(context ,message.toString(), Toast.LENGTH_LONG).show()
                productViewModel.onFailFetchProducts()
            }
        })

        binding.viewmodel?.menufilterButton?.observe(viewLifecycleOwner, Observer { it ->
            if (it != null) {
                showRecyclerViewProduct()
                hideMenuItems()
            }else{
                showMenuItems()
            }
        })


        productViewModel.searchFilter.observe(viewLifecycleOwner, Observer { textFilter ->
            if(textFilter != null){
                searchView.setQuery(textFilter, false)
                showRecyclerViewProduct()
                hideMenuItems()
            }
        })

        productViewModel.navigateToProductDetail.observe(viewLifecycleOwner, Observer { product ->
            if(product != null) {
                product.let { it ->
                    this.findNavController().navigate(
                        ProductFragmentDirections
                            .actionNavProductToProductDetailFragment(product))
                    productViewModel.onProductNavigated()
                }
            }
        })

        productViewModel.navigateToEditProduct.observe(viewLifecycleOwner, Observer{ product ->
            if(product != null) {
                product.let {
                    this.findNavController().navigate(
                        ProductFragmentDirections.actionNavProductToEditProductFragment(product))
                    productViewModel.onEditProductNavigated()
                }
            }
        })


        productViewModel.deleteProduct.observe(viewLifecycleOwner, Observer { product ->
        if(product != null){
            product.let { product ->
                MaterialAlertDialogBuilder(requireContext())
                    .setTitle("Eliminar producto")
                    .setMessage("¿Estase seguro que deseas Eliminar ${product.model}?")
                    .setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, which ->
                            productViewModel.deleteProduct(product.id)
                    })
                    .setNegativeButton("No", null).show()

                productViewModel.onProductDeleted()
            }
        }

        })

        productViewModel.failedDeleteProduct.observe(viewLifecycleOwner, Observer { message->
            if(message != null){
                message.let {
                    Toast.makeText(context ,message.toString(), Toast.LENGTH_LONG).show()
                }
            }
        })
    }


    override fun onStart() {
        super.onStart()

        searchView = activity?.findViewById(R.id.searchView)!!
        searchView.visibility = View.VISIBLE
        searchView.setOnQueryTextListener(this)
        (activity as BaseActivity).disableBottomNav()
    }

    override fun onStop() {
        super.onStop()
        searchView.visibility = View.GONE

        Timber.d("Stop product fragment")
    }


    override fun onDestroy() {
        super.onDestroy()

        Timber.d("Destry product fragment")
    }


    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }


    /**
     * Show or Hide RecyclerViews on a FragmentProduct when use a SearchView
     */
    override fun onQueryTextChange(newText: String?): Boolean {

        if(newText?.length != 0){
            hideMenuItems()
            productViewModel.searchViewFilter(newText)
        }else{
            hideRecyclerViewProduct()
            showMenuItems()
        }

        return true
    }

    private fun showRecyclerViewProduct(){
        binding.swipeRefreshLayout.visibility = View.VISIBLE
        binding.recyclerviewProduct.visibility = View.VISIBLE
    }

    private fun hideRecyclerViewProduct(){
        binding.swipeRefreshLayout.visibility = View.GONE
        binding.recyclerviewProduct.visibility = View.GONE
    }

    private fun showMenuItems(){
        binding.recyclerviewMenu.visibility = View.VISIBLE
    }

    private fun hideMenuItems(){
        binding.recyclerviewMenu.visibility = View.GONE
    }

    private fun showProgressBar(){
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar(){
        binding.progressBar.visibility = View.GONE
    }
}