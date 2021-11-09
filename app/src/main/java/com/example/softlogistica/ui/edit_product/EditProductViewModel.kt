package com.example.softlogistica.ui.edit_product

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.*
import com.example.softlogistica.model.product.Product
import com.example.softlogistica.model.product.ProductDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EditProductViewModel(val product: Product, val database: ProductDao, application: Application, savedStateHandle: SavedStateHandle) : AndroidViewModel(application) {

        var editProduct = MutableLiveData<Product>()

        private val _onProductUpdated = MutableLiveData<Boolean>()
        val onProductUpdated : LiveData<Boolean> = _onProductUpdated

        private val _failUpdateProduct = MutableLiveData<String>()
        val failUpdateProduct : LiveData<String> = _failUpdateProduct



    init {
        editProduct.value = product

    }

   @SuppressLint("LogNotTimber")
   fun updateProduct(){
       viewModelScope.launch {
           withContext(Dispatchers.IO){
               try{
                   database.update(editProduct.value as Product)
                   _onProductUpdated.value = true
               }catch (e : Exception){
                    _failUpdateProduct.value = e.message
               }
           }
       }
    }


    fun onFailProductUpdated(){
        _failUpdateProduct.value = null
    }

}