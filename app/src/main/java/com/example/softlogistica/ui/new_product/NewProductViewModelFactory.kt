package com.example.softlogistica.ui.new_product

import android.app.Application
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Database
import com.example.softlogistica.model.product.ProductDao
import java.lang.IllegalArgumentException

class NewProductViewModelFactory(private val dataSource: ProductDao,
private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(NewProductViewModel::class.java)) {
            return NewProductViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Unkow ViewModel Data")
    }
}