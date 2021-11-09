package com.example.softlogistica.ui.services

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.softlogistica.model.product.ProductDao

class ServiceViewModelFactory(private val dataSource: ProductDao, private val application : Application) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ServiceViewModel::class.java)){
            return ServiceViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Unknow ViewModel class")
    }

}