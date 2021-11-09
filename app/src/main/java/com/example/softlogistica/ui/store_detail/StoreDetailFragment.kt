package com.example.softlogistica.ui.store_detail

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModelProvider
import com.example.softlogistica.R
import com.example.softlogistica.database.ApplicationDatabase
import com.example.softlogistica.databinding.FragmentProductDetailStoreBinding
import com.example.softlogistica.model.product.Product
import com.example.softlogistica.ui.product_detail.ProductDetailFragmentArgs
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.*

class StoreDetailFragment : Fragment(){

    private lateinit var storeDetailViewModel: StoreDetailViewModel
    private lateinit var  binding : FragmentProductDetailStoreBinding
    private lateinit var badgeCart: BadgeDrawable
    private lateinit var bottomNav : BottomNavigationView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val productDetail = ProductDetailFragmentArgs.fromBundle(requireArguments()).product
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_product_detail_store,container, false)

        val application = requireNotNull(this.activity).application
        val datasource = ApplicationDatabase.getInstance(application).productDatabaseDao
        val viewModelFactory = StoreDetailViewModelFactory(productDetail, datasource, application, SavedStateHandle())

        storeDetailViewModel = ViewModelProvider(this, viewModelFactory).get(
            StoreDetailViewModel::class.java)

        binding.viewmodel = storeDetailViewModel
        binding.lifecycleOwner = this

        observers()

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        bottomNav = activity?.findViewById(R.id.bottom_navigation)!!
    }


   private fun observers(){

       storeDetailViewModel.cartListProducts.observe(viewLifecycleOwner, Observer { cartListProducts ->
           badgeCart = bottomNav.getOrCreateBadge(R.id.bottom_nav_cart)
           cartListProducts?.let {list ->
               if(list.size > 0 ){
                   badgeCart.isVisible = true
                   badgeCart.number = list.size
               }else{
                   badgeCart.isVisible = false
               }
           }
       })

        storeDetailViewModel.productAlreadyInCart.observe(viewLifecycleOwner, Observer {
            if(it != null){
                Toast.makeText(requireContext(),"El prducto ya esta en el carrito", Toast.LENGTH_SHORT).show()
            }
        })

        storeDetailViewModel.buyRentalProductModal.observe(viewLifecycleOwner, Observer { product ->

            if (product != null){
                product.let {
                    AlertDialog.Builder(requireActivity())
                        .setTitle("¿Desea comprar o alquilar el producto?")
                        .setPositiveButton("COMPRAR", DialogInterface.OnClickListener { dialog, which ->
                            storeDetailViewModel.onBuyProduct(product)
                            storeDetailViewModel.onBuyRentalProductModalCleared()
                        })
                        .setNeutralButton("Alquilar", DialogInterface.OnClickListener { dialog, which ->
                            showDatePicker(product)
                        }).show()
                }
                storeDetailViewModel.onBuyRentalProductModalCleared()
            }
        })


        storeDetailViewModel.failFetchProduct.observe(viewLifecycleOwner, Observer { message ->
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        })
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
                storeDetailViewModel.onRentalProduct(product, firDateFormat, secondDateFormat )
            }
        }.show(requireActivity().supportFragmentManager, MaterialDatePicker::class.java.canonicalName)
    }



}