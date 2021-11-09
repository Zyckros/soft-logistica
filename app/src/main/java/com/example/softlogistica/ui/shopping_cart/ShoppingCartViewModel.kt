package com.example.softlogistica.ui.shopping_cart

import android.app.Application
import androidx.lifecycle.*
import com.example.softlogistica.model.cart.Cart
import com.example.softlogistica.model.product.Product
import com.example.softlogistica.model.product.ProductDao
import kotlinx.coroutines.launch
import timber.log.Timber

class ShoppingCartViewModel(val database: ProductDao, application: Application, savedStateHandle: SavedStateHandle, val shoppingCartRepository: ShoppingCartRepository) : AndroidViewModel(application)  {

    private var _cartListProducts = MutableLiveData<MutableList<Product?>?>()
    val cartListProducts : LiveData<MutableList<Product?>?> = _cartListProducts

    private var _cart = MutableLiveData<MutableList<Cart?>>()
    val cart : LiveData<MutableList<Cart?>> = _cart

    private val _navigateToProductDetail = MutableLiveData<Product>()
    val navigateToProductDetailStore: LiveData<Product> = _navigateToProductDetail

    init {
        refreshCartList()
    }

    private fun refreshCartList(){
        viewModelScope.launch{
            try {
                shoppingCartRepository.refreshCartList()
                _cart.value = shoppingCartRepository.getAllShoppingCart()
                _cartListProducts.value = shoppingCartRepository.cartListProducts()
                Timber.i("Success refresh cart list:")
            }catch (e: Exception){
                Timber.i("fail refresh cart list:  ${e.message}")
            }
        }
    }

    fun onDeletedProductCartList(product: Product) {
        viewModelScope.launch {
            shoppingCartRepository.removeCartListProduct(product.id)
        }
        refreshCartList()
    }

    fun finishPurchase(){
        viewModelScope.launch {
            shoppingCartRepository.emptyShoppingCart()
            _cartListProducts.value = mutableListOf()
        }
    }



    fun onProductCartClicked(product: Product) {
        _navigateToProductDetail.value = product
    }


    fun onProductNavigated(){
        _navigateToProductDetail.value = null
    }

}