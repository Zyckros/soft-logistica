package com.example.softlogistica.ui.store

import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.softlogistica.BaseActivity
import com.example.softlogistica.MenuActivity
import com.example.softlogistica.R
import com.example.softlogistica.database.ApplicationDatabase
import com.example.softlogistica.databinding.FragmentStoreBinding
import com.example.softlogistica.model.product.Product
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.datepicker.MaterialDatePicker
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

class StoreFragment : Fragment() , SearchView.OnQueryTextListener {


    private lateinit var badgeCart: BadgeDrawable
    private lateinit var bottomNav : BottomNavigationView
    private var listProductsCart : ConstraintLayout? = null
    private var searchView: SearchView? = activity?.findViewById(R.id.searchView)
    private lateinit var binding : FragmentStoreBinding
    private lateinit var storeViewModel : StoreViewModel
    private lateinit var storeProductAdapter : StoreProductAdapter
    private lateinit var menuStoreAdapter : MenuStoreAdapter
    private var layoutStore: ConstraintLayout? = null


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        Timber.plant(Timber.DebugTree())
        val application = requireNotNull(this.activity).application
        val dataSource =  ApplicationDatabase.getInstance(application).productDatabaseDao
        val cartDao = ApplicationDatabase.getInstance(application).cartListDao
        val viewModelFactory = StoreViewModelFactory(dataSource, cartDao, application, SavedStateHandle())

        binding = FragmentStoreBinding.inflate(inflater, container, false)
        storeViewModel = ViewModelProvider(this, viewModelFactory).get(StoreViewModel::class.java)
        binding.lifecycleOwner = this
        binding.viewmodel = storeViewModel
        recyclerViewProduct()
        recyclerViewMenu()
        bindingObservers()
        onBackPressed()


        return binding.root
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun onBackPressed() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner){
            if(binding.recyclerviewProductStore.isVisible){
                showMenuItems()
                searchView?.setQuery("",false)
                searchView?.onActionViewCollapsed()
                storeViewModel.onSearchFiltered()
                hideRecyclerViewProduct()
                storeViewModel.onMenuFiltered()
                storeViewModel.onMenuFiltered()
            }else if(binding.recyclerviewMenuStore.isVisible){
                if (searchView?.hasFocus() == true){
                    searchView?.setQuery("",false)
                    searchView?.clearFocus()
                    searchView?.onActionViewCollapsed()
                }else {
                    val intent = Intent(Intent.ACTION_MAIN)
                        .addCategory(Intent.CATEGORY_HOME)
                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                }
            }
        }
    }


    /**
     * Setting Observers of a ViewModel variables
     */
    private fun bindingObservers(){


        storeViewModel.products.observe(viewLifecycleOwner, Observer { list ->
        })

        storeViewModel.cartListProducts.observe(viewLifecycleOwner, Observer { cartListProducts ->

            cartListProducts?.let {list ->
                badgeCart = bottomNav.getOrCreateBadge(R.id.bottom_nav_cart)

                if(list.size > 0 ){
                    badgeCart.isVisible = true
                    badgeCart.number = list.size
                }else{
                    badgeCart.isVisible = false
                }
            }
        })

        storeViewModel.cart.observe(viewLifecycleOwner, Observer { list ->
            list?.let {

            }
        })


        storeViewModel.productAddedToCart.observe(viewLifecycleOwner, Observer { status ->
            if (status != null){
                status.let {
                    Toast.makeText(requireContext(),"El producto se ha añadido al carrito correctamente", Toast.LENGTH_SHORT).show()
                }
                storeViewModel.onProductAddedtoCart()
            }
        })

        storeViewModel.productDeletedToCart.observe(viewLifecycleOwner, Observer { status ->
            if (status != null){
                status.let {
                    Toast.makeText(requireContext(),"El producto se ha eliminado del carrito correctamente", Toast.LENGTH_SHORT).show()
                }
                storeViewModel.onProductDeletedToCart()
            }
        })

        storeViewModel?.showMenuItems.observe(viewLifecycleOwner, Observer{ status ->
            if(status != null){
                showMenuItems()
            }
        })

        storeViewModel?.hideMenuItems.observe(viewLifecycleOwner, Observer{ status ->
            if(status != null){
                hideMenuItems()
            }
        })


        binding.viewmodel?.showProgressBar?.observe(viewLifecycleOwner, Observer{ status ->
            if(status != null){
                showProgressBar()
                storeViewModel.onProgressBarShowed()
            }
        })

        binding.viewmodel?.hideProgressBar?.observe(viewLifecycleOwner, Observer{ status ->
            if(status != null){
                hideProgressBar()
                storeViewModel.onProgressBarHidden()
            }
        })

        binding.viewmodel?.showRecyclerViewProducts?.observe(viewLifecycleOwner, Observer { status ->
            if(status != null){
                showRecyclerViewProduct()
                storeViewModel.onShowRecyclerViewProduct()
            }
        })

        binding.viewmodel?.hideRecyclerViewProducts?.observe(viewLifecycleOwner, Observer { status ->
            if(status != null){
                hideRecyclerViewProduct()
                storeViewModel.onHideRecyclerViewProduct()
            }
        })




        storeViewModel.refreshLayout.observe(viewLifecycleOwner, Observer { status ->
            if(status != null){
                status.let {
                    binding.swipeRefreshLayout.isRefreshing = false
                }
            }
        })


        storeViewModel.failFetchProducts.observe(viewLifecycleOwner, Observer { message ->
            if(message != null){
                Toast.makeText(context ,message, Toast.LENGTH_LONG).show()
                storeViewModel.onFailFetchProducts()
            }
        })


        storeViewModel?.menufilterButton?.observe(viewLifecycleOwner, Observer { it ->
            if (it != null) {
                showRecyclerViewProduct()
                hideMenuItems()
            }else{
                showMenuItems()
            }
        })


        storeViewModel.searchFilter.observe(viewLifecycleOwner, Observer { textFilter ->
            if(textFilter != null){
                searchView?.setQuery(textFilter, false)
                showRecyclerViewProduct()
                hideMenuItems()
            }
        })


        storeViewModel.productAlreadyInCart.observe(viewLifecycleOwner, Observer {
            if(it != null){
                Toast.makeText(requireContext(),"El prducto ya esta en el carrito", Toast.LENGTH_SHORT).show()

            }
        })


        storeViewModel.buyRentalProductModal.observe(viewLifecycleOwner, Observer { product ->

            if (product != null){
                product.let {
                    AlertDialog.Builder(requireActivity())
                        .setTitle("¿Desea comprar o alquilar el producto?")
                        .setPositiveButton("COMPRAR", DialogInterface.OnClickListener { dialog, which ->
                            storeViewModel.onBuyProduct(product)
                            storeViewModel.onBuyRentalProductModalCleared()
                        })
                        .setNeutralButton("Alquilar", DialogInterface.OnClickListener { dialog, which ->
                            showDatePicker(product)
                        }).show()
                }
                storeViewModel.onBuyRentalProductModalCleared()
            }
        })


        storeViewModel.navigateToProductDetailStore.observe(viewLifecycleOwner, Observer { product ->
            if(product != null) {
                product.let { it ->
                    this.findNavController().navigate(
                        StoreFragmentDirections.actionNavStoreToStoreDetailFragment(product))
                    storeViewModel.onProductNavigated()
                }
            }
        })
    }



    /**
     * Init recycler View Menu setting elements
     */
    private fun recyclerViewMenu() {
        menuStoreAdapter = MenuStoreAdapter(MenuStoreListener { menu ->
            storeViewModel.onProductMenuClicked(menu)
        })
        binding.recyclerviewMenuStore.adapter = menuStoreAdapter
        binding.recyclerviewMenuStore.layoutManager =
            GridLayoutManager(activity, 2, GridLayoutManager.VERTICAL, false)
    }


    /**
     * Setting Listeners on a RecyclerView Product
     */
    private fun recyclerViewProduct() {
        storeProductAdapter = StoreProductAdapter(StoreProductListener { product ->
            storeViewModel.onProductClicked(product)
        }, AddProductStoreCartClickListener { product ->
            storeViewModel.onAddProductCartClicked(product)
        })

        binding.recyclerviewProductStore.adapter = storeProductAdapter
        binding.recyclerviewProductStore.layoutManager = GridLayoutManager(activity, 1, GridLayoutManager.VERTICAL, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }



    override fun onStart() {
        super.onStart()
        (activity as BaseActivity).disableBottomNav()
        searchView = (activity as MenuActivity)?.findViewById(R.id.searchView)
        searchView?.visibility = View.VISIBLE
        searchView?.setOnQueryTextListener(this)
        layoutStore = (activity as MenuActivity)?.findViewById(R.id.layout_store)
     }


    private fun showShoppingCart() {
        Timber.i("List cart : ${listProductsCart.toString()}")
        listProductsCart?.visibility = View.VISIBLE
        layoutStore?.visibility = View.GONE
    }

    override fun onStop() {
        super.onStop()
        searchView?.visibility = View.GONE
    }


    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {

        if(newText?.length != 0){
            hideMenuItems()
        }else{
            hideRecyclerViewProduct()
            showMenuItems()
        }

        storeViewModel.searchViewFilter(newText)

        return true
    }

    private fun showRecyclerViewProduct(){
        binding.swipeRefreshLayout.visibility = View.VISIBLE
        binding.recyclerviewProductStore.visibility = View.VISIBLE
    }

    private fun hideRecyclerViewProduct(){
        binding.swipeRefreshLayout.visibility = View.GONE
        binding.recyclerviewProductStore.visibility = View.GONE
    }

    private fun showMenuItems(){
        binding.recyclerviewMenuStore.visibility = View.VISIBLE
    }

    private fun hideMenuItems(){
        binding.recyclerviewMenuStore.visibility = View.GONE
    }

    private fun showProgressBar(){
        binding.progressBarStore.visibility = View.VISIBLE
    }

    private fun hideProgressBar(){
        binding.progressBarStore.visibility = View.GONE
    }

    private fun showDatePicker(product: Product){
        var currentSelectedStartDate: Long? = null
        var currentSelectedEndDate: Long? = null



        val selectedStartDateInMillis = currentSelectedStartDate ?: System.currentTimeMillis()
        val selectedEndDateInMillis = currentSelectedEndDate ?: System.currentTimeMillis()
        val dateRange = androidx.core.util.Pair(selectedStartDateInMillis, selectedEndDateInMillis)

        MaterialDatePicker.Builder.dateRangePicker().setSelection(dateRange).build().apply {
            addOnPositiveButtonClickListener {dateRange ->
                val format = SimpleDateFormat("dd-MM-yyyy")
                val firstDate = Date(dateRange.first)
                val secondDate = Date(dateRange.second)
                val firDateFormat = format.format(firstDate)
                val secondDateFormat = format.format(secondDate)
                storeViewModel.onRentalProduct(product, firDateFormat, secondDateFormat )
            }
        }.show(requireActivity().supportFragmentManager, MaterialDatePicker::class.java.canonicalName)
    }
}