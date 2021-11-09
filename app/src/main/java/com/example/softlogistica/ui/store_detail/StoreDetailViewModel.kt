package com.example.softlogistica.ui.store_detail

import android.app.Application
import androidx.lifecycle.*
import com.example.softlogistica.database.ApplicationDatabase
import com.example.softlogistica.model.cart.Cart
import com.example.softlogistica.model.product.Product
import com.example.softlogistica.model.product.ProductDao
import kotlinx.coroutines.launch
import timber.log.Timber

class StoreDetailViewModel(val product: Product, val database: ProductDao, application: Application, savedStateHandle: SavedStateHandle): AndroidViewModel(application) {


    private val storeDetailRepository = StoreDetailRepository(ApplicationDatabase.getInstance(application))

    private val _productDetail = MutableLiveData<Product>()
    val productDetail: LiveData<Product> = _productDetail

    private val  _failFetchProduct = MutableLiveData<String>()
    val failFetchProduct : LiveData<String> = _failFetchProduct

    private val _buyRentalProductModal = MutableLiveData<Product>()
    val buyRentalProductModal : LiveData<Product> = _buyRentalProductModal

    private var _cartListProducts = MutableLiveData<MutableList<Product?>?>()
    val cartListProducts : LiveData<MutableList<Product?>?> = _cartListProducts

    private var _productAlreadyInCart = MutableLiveData<Boolean?>()
    val productAlreadyInCart : LiveData<Boolean?> = _productAlreadyInCart

    private var _cart = MutableLiveData<MutableList<Cart?>>()
    val cart : LiveData<MutableList<Cart?>> = _cart

    init{
        _productDetail.value = product
        refreshCartList()
    }

    private fun getProduct() {
        viewModelScope.launch {
            try{
                _productDetail.value =  database.get(product.id) //Service.retrofitService.get(1L) //
            }catch(e: Exception){
                _failFetchProduct.value = e.message
            }
        }
    }

    fun onBuyProduct(product: Product){
        if(_cartListProducts.value?.contains(product) == false){
            viewModelScope.launch {
                storeDetailRepository.addCartListProduct(Cart(id =null,  product_id = product.id,init_rental_date = null,end_rental_date = null,"buy"))
            }
            refreshCartList()
        }
    }

    fun onBuyRentalProductModalCleared() {
        _buyRentalProductModal.value = null
    }

    fun buyOrRentalProduct() {

    }

    fun onRentalProduct(product : Product, firstDate : String, secondDate: String) {
        viewModelScope.launch {
            storeDetailRepository.addCartListProduct(Cart(id =null,  product_id = product.id, init_rental_date = firstDate,end_rental_date = secondDate,"rental"))
        }
        refreshCartList()
    }

    private fun refreshCartList(){
        viewModelScope.launch{
            try {
                storeDetailRepository.refreshCartList()
                _cart.value = storeDetailRepository.getAllShoppingCart()
                _cartListProducts.value = storeDetailRepository.cartListProducts()
                Timber.i("Success refresh cart list:")
            }catch (e: Exception){
                Timber.i("fail refresh cart list:  ${e.message}")
            }
        }
    }

    fun onAddProductCartClicked(product: Product){
        if(_cartListProducts.value?.contains(product) == true){
            _productAlreadyInCart.value = true
        }else{
            _buyRentalProductModal.value = product
        }
    }
}