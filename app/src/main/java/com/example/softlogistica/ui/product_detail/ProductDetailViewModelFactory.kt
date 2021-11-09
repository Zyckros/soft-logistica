package com.example.softlogistica.ui.product_detail

import android.app.Application
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.softlogistica.model.product.Product
import com.example.softlogistica.model.product.ProductDao

class ProductDetailViewModelFactory(private val product: Product, private val datasource: ProductDao, private val application: Application, private var savedStateHandle: SavedStateHandle): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ProductDetailViewModel::class.java)){
            return ProductDetailViewModel(product, datasource,application,savedStateHandle) as T
        }
        throw IllegalArgumentException("Unknow viewmodel class")
    }
}