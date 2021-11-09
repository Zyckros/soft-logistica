package com.example.softlogistica.ui.store

import android.app.Application
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.softlogistica.model.cart.CartDao
import com.example.softlogistica.model.product.ProductDao

class StoreViewModelFactory(private val datasource: ProductDao, private val cartDao : CartDao, private val application: Application, private val savedStateHandle: SavedStateHandle) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(StoreViewModel::class.java)){
            return StoreViewModel(datasource, cartDao, application, savedStateHandle) as T
        }

        throw IllegalArgumentException("Unknow ViewModel class")
    }
}