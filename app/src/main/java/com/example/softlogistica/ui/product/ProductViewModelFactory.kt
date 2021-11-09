package com.example.softlogistica.ui.product

import android.app.Application
import android.content.SharedPreferences
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.softlogistica.model.product.ProductDao

class ProductViewModelFactory(private val datasource: ProductDao, private val application: Application, private var savedStateHandle: SavedStateHandle, private val sharedPrefs: SharedPreferences, private val productRepository: ProductRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(ProductViewModel::class.java)){
                return ProductViewModel(datasource, application, savedStateHandle, sharedPrefs, productRepository) as T
            }
        throw IllegalArgumentException("Unknow ViewModel class")
    }
}