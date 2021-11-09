package com.example.softlogistica.ui.store_detail

import android.app.Application
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.softlogistica.model.product.Product
import com.example.softlogistica.model.product.ProductDao

class StoreDetailViewModelFactory(private val product: Product, private val datasource: ProductDao, private val application: Application, private var savedStateHandle: SavedStateHandle): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(StoreDetailViewModel::class.java)){
            return StoreDetailViewModel(product, datasource,application,savedStateHandle) as T
        }
        throw IllegalArgumentException("Unknow viewmodel class")
    }
}