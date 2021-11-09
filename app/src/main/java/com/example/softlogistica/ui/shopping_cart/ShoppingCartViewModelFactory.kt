package com.example.softlogistica.ui.shopping_cart

import android.app.Application
import android.content.SharedPreferences
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.softlogistica.model.product.ProductDao

class ShoppingCartViewModelFactory(private val datasource: ProductDao, private val application: Application, private var savedStateHandle: SavedStateHandle, private val shoppingCartRepository: ShoppingCartRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ShoppingCartViewModel::class.java)){
            return ShoppingCartViewModel(datasource, application, savedStateHandle, shoppingCartRepository) as T
        }
        throw IllegalArgumentException("Unknow ViewModel class")
    }
}