package com.example.softlogistica.ui.product_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.softlogistica.R
import com.example.softlogistica.database.ApplicationDatabase
import com.example.softlogistica.databinding.FragmentProductDetailBinding
import com.example.softlogistica.model.product.Product


class ProductDetailFragment : androidx.fragment.app.Fragment() {

    private lateinit var productDetailViewModel: ProductDetailViewModel
    private lateinit var  binding : FragmentProductDetailBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val productDetail = ProductDetailFragmentArgs.fromBundle(requireArguments()).product
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_product_detail,container, false)

        val application = requireNotNull(this.activity).application
        val datasource = ApplicationDatabase.getInstance(application).productDatabaseDao
        val viewModelFactory = ProductDetailViewModelFactory(productDetail, datasource, application, SavedStateHandle())

        productDetailViewModel = ViewModelProvider(this, viewModelFactory).get(ProductDetailViewModel::class.java)

        binding.viewmodel = productDetailViewModel
        binding.lifecycleOwner = this

        binding.floatingButtonEditProduct.setOnClickListener {
            productDetailViewModel.oneditProductClicked()
        }

        productDetailViewModel.failFetchProduct.observe(viewLifecycleOwner, Observer { message ->
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        })

        productDetailViewModel.navigateToEditProduct.observe(viewLifecycleOwner, Observer {
            if(it != null){
                this.findNavController().navigate(
                    ProductDetailFragmentDirections.actionProductDetailFragmentToEditProductFragment(productDetailViewModel.productDetail.value as Product))
                productDetailViewModel.onEditProductNavigated()
            }
        })

        return binding.root
    }


}