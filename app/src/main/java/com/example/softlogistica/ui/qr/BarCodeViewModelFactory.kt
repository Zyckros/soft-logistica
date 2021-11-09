package com.example.softlogistica.ui.qr

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.softlogistica.model.product.ProductDao

class BarCodeViewModelFactory(private val datasource: ProductDao, private val application: Application,  private val barCodeRepository: BarCodeRepository): ViewModelProvider.Factory  {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(BarCodeViewModel::class.java)){
            return BarCodeViewModel(datasource, application, barCodeRepository) as T
        }
        throw IllegalArgumentException("Unknow ViewModel class")
    }
}