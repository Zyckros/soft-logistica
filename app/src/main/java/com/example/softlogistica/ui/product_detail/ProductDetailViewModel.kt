package com.example.softlogistica.ui.product_detail

import android.app.Application
import androidx.lifecycle.*
import com.example.softlogistica.model.product.Product
import com.example.softlogistica.model.product.ProductDao
import kotlinx.coroutines.*

class ProductDetailViewModel(val product: Product, val database:ProductDao, application: Application, savedStateHandle: SavedStateHandle): AndroidViewModel(application) {

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _navigateToEditProduct = MutableLiveData<Boolean>()
    val navigateToEditProduct: LiveData<Boolean> = _navigateToEditProduct

    private val _productDetail = MutableLiveData<Product>()
    val productDetail: LiveData<Product> = _productDetail

    private val  _failFetchProduct = MutableLiveData<String>()
    val failFetchProduct : LiveData<String> = _failFetchProduct

    init{
        _productDetail.value = product
        //getProduct()
    }

    private fun getProduct() {

        uiScope.launch {
            try{
                _productDetail.value =  database.get(product.id)//Service.retrofitService.get(1L) //
            }catch(e: Exception){
                _failFetchProduct.value = e.message
            }
        }
    }


    fun oneditProductClicked(){
        _navigateToEditProduct.value = true
    }

    fun onEditProductNavigated(){
        _navigateToEditProduct.value = null
    }

}