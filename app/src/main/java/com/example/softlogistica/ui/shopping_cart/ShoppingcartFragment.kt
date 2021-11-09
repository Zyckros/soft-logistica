package com.example.softlogistica.ui.shopping_cart

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.softlogistica.BaseActivity
import com.example.softlogistica.R
import com.example.softlogistica.database.ApplicationDatabase
import com.example.softlogistica.databinding.FragmentShoppingCartBinding
import com.example.softlogistica.ui.store.CartListAdapter
import com.example.softlogistica.ui.store.DeleteProductCartClickListener
import com.example.softlogistica.ui.store.ProductCartClickListener
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.bottomnavigation.BottomNavigationView

class ShoppingcartFragment : Fragment() {

    private lateinit var binding : FragmentShoppingCartBinding
    private lateinit var shoppingCartViewModel: ShoppingCartViewModel
    private lateinit var cartListAdapter: CartListAdapter
    private lateinit var badgeCart: BadgeDrawable
    private lateinit var bottomNav : BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val application = requireNotNull(this.activity).application
        val dataSource =  ApplicationDatabase.getInstance(application).productDatabaseDao
        val shoppingCartRepository =  ShoppingCartRepository(ApplicationDatabase.getInstance(application))
        val viewModelFactory = ShoppingCartViewModelFactory(dataSource, application, SavedStateHandle(), shoppingCartRepository)


        binding =  FragmentShoppingCartBinding.inflate(inflater, container, false)
        shoppingCartViewModel = ViewModelProvider(this, viewModelFactory).get(ShoppingCartViewModel::class.java)
        binding.viewmodel = shoppingCartViewModel
        binding.lifecycleOwner = this

        observers()
        recyclerViewCartList()

        binding.buttonStore.setOnClickListener {
            this.findNavController().navigate(ShoppingcartFragmentDirections.actionShoppingcartFragmentToStoreFragment())
        }

        binding.finalButtonCart.setOnClickListener {
            AlertDialog.Builder(requireActivity())
                .setTitle("¿Confirma finalizar la compra y solicitar un presupuesto?")
                .setPositiveButton("FINALIZAR", DialogInterface.OnClickListener { dialog, which ->

                    shoppingCartViewModel.finishPurchase()
                    /**
                     * TODO - USAR ESTE TOAST CUANDO SE ALMACENE LA PROPUESTA CON ÉXITO
                     */
                    Toast.makeText(requireContext(), "La solcitud de compra se ha enviado con éxito, recibiras una notificación cuando el presupuesto este listo.", Toast.LENGTH_LONG).show()
                    redirectToHome()

                })
                .setNeutralButton("CANCELAR", DialogInterface.OnClickListener { dialog, which ->

                }).show()

        }

        return binding.root
    }



    private fun redirectToHome() {
        this.findNavController().navigate(
            ShoppingcartFragmentDirections.actionShoppingcartFragmentToNavHome()
        )
    }


    override fun onStart() {
        super.onStart()
        bottomNav = activity?.findViewById(R.id.bottom_navigation)!!
        (activity as BaseActivity).enableBottomNAv()
    }


    private fun recyclerViewCartList() {
        cartListAdapter = CartListAdapter(DeleteProductCartClickListener { product ->
            shoppingCartViewModel.onDeletedProductCartList(product)
        }, ProductCartClickListener { product ->
            shoppingCartViewModel.onProductCartClicked(product)
        })
        binding.recyclerviewProductsCart.adapter = cartListAdapter
        binding.recyclerviewProductsCart.layoutManager = GridLayoutManager(activity, 1, GridLayoutManager.VERTICAL, false)
    }


    private fun observers(){

        shoppingCartViewModel.cartListProducts.observe(viewLifecycleOwner, Observer { cartListProducts ->
            badgeCart = bottomNav.getOrCreateBadge(R.id.bottom_nav_cart)
            cartListProducts?.let {list ->
                if(list.size > 0 ){
                    badgeCart.isVisible = true
                    badgeCart.number = list.size
                    showListCart()
                }else{
                    showEmptyCartLayout()
                    badgeCart.isVisible = false
                }
            }
        })


        shoppingCartViewModel.navigateToProductDetailStore.observe(viewLifecycleOwner, Observer { product ->
            if(product != null) {
                product.let { it ->
                    this.findNavController().navigate(
                        ShoppingcartFragmentDirections.actionShoppingcartFragmentToProductDetailFragment(product))
                    shoppingCartViewModel.onProductNavigated()
                }
            }
        })
    }

    private fun showEmptyCartLayout() {
        binding.empyCartLayout.visibility = View.VISIBLE
        binding.framelayoutListCart.visibility = View.GONE
    }

    private fun showListCart() {
        binding.empyCartLayout.visibility = View.GONE
        binding.framelayoutListCart.visibility = View.VISIBLE
    }
}