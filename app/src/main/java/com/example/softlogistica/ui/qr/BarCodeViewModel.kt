package com.example.softlogistica.ui.qr

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.softlogistica.model.product.Product
import com.example.softlogistica.model.product.ProductDao
import kotlinx.coroutines.launch

class BarCodeViewModel(val database: ProductDao, application: Application, val barCodeRepository: BarCodeRepository) : AndroidViewModel(application)  {

    private var _products = MutableLiveData<Product?>()
    val products : LiveData<Product?> = _products

    private var _message = MutableLiveData<String?>()
    val message : LiveData<String?> = _message


    fun barCodeScannerProduct(id : Long?) {
        viewModelScope.launch {
            _products.value = barCodeRepository.product(id)
        }
    }

    fun onDetailProductNavigated() {
        _products.value = null
    }

    fun onMessageShowed() {
        _message.value = null
    }

    fun notFoundProduct() {
        _message.value = "No se encuentra el producto en la base de datos"
    }

}