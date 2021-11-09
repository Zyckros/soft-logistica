package com.example.softlogistica.ui.edit_product

import android.app.Application
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.softlogistica.model.product.Product
import com.example.softlogistica.model.product.ProductDao
import com.example.softlogistica.ui.product.ProductViewModel

class EditProductViewModelFactory(private val editProduct: Product ,private val dataSource: ProductDao, private val application: Application, private var savedStateHandle: SavedStateHandle) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(EditProductViewModel::class.java)){
            return EditProductViewModel(editProduct, dataSource, application, savedStateHandle) as T
        }
        throw IllegalArgumentException("Unknow ViewModel class")
    }

}