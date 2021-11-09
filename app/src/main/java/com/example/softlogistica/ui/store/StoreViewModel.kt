package com.example.softlogistica.ui.store

import android.app.Application
import androidx.lifecycle.*
import com.example.softlogistica.database.ApplicationDatabase
import com.example.softlogistica.model.cart.Cart
import com.example.softlogistica.model.cart.CartDao
import com.example.softlogistica.model.menu.ProductMenu
import com.example.softlogistica.model.product.Product
import com.example.softlogistica.model.product.ProductDao
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber

class StoreViewModel(val database: ProductDao, val cartDao : CartDao,  application: Application, savedStateHandle: SavedStateHandle) : AndroidViewModel(application) {


    private var viewModelJob = Job()

    private val storeRepository = StoreRepository(ApplicationDatabase.getInstance(application))

    private var _cart = MutableLiveData<MutableList<Cart?>>()
    val cart : LiveData<MutableList<Cart?>> = _cart

    private var _cartListProducts = MutableLiveData<MutableList<Product?>?>()
    val cartListProducts : LiveData<MutableList<Product?>?> = _cartListProducts

    private var _productAlreadyInCart = MutableLiveData<Boolean?>()
            val productAlreadyInCart : LiveData<Boolean?> = _productAlreadyInCart

    private var _products = MutableLiveData<List<Product?>?> ()
    val products : LiveData<List<Product?>?> = _products


    private val _menuItems = MutableLiveData<List<ProductMenu?>>()
    val menuItems : LiveData<List<ProductMenu?>> = _menuItems

    private val _productAddedToCart = MutableLiveData<Boolean>()
    val productAddedToCart : LiveData<Boolean> = _productAddedToCart

    private val _productDeletedToCart = MutableLiveData<Boolean>()
    val productDeletedToCart : LiveData<Boolean> = _productDeletedToCart

    private val _showRecyclerViewProducts = MutableLiveData<Boolean>()
    val showRecyclerViewProducts : LiveData<Boolean> = _showRecyclerViewProducts

    private val _hideRecyclerViewProducts = MutableLiveData<Boolean>()
    val hideRecyclerViewProducts : LiveData<Boolean> = _hideRecyclerViewProducts

    private val _showProgressBar = MutableLiveData<Boolean>()
    val showProgressBar : LiveData<Boolean> = _showProgressBar

    private val _hideProgressBar = MutableLiveData<Boolean>()
    val hideProgressBar : LiveData<Boolean> = _hideProgressBar

    private val _showMenuItems = MutableLiveData<Boolean>()
    val showMenuItems : LiveData<Boolean> = _showMenuItems

    private val _hideMenuItems = MutableLiveData<Boolean>()
    val hideMenuItems : LiveData<Boolean> = _hideMenuItems

    private val _refreshLayout = MutableLiveData<Boolean>()
    val refreshLayout : LiveData<Boolean> = _refreshLayout

    private val _menufilterButton = MutableLiveData<Long>()
    val menufilterButton : LiveData<Long> = _menufilterButton

    private val _searchFilter = MutableLiveData<String>()
    val searchFilter : LiveData<String> = _searchFilter

    private val _failFetchProducts = MutableLiveData<String>()
    val failFetchProducts: LiveData<String> = _failFetchProducts

    private val _navigateToProductDetail = MutableLiveData<Product>()
    val navigateToProductDetailStore: LiveData<Product> = _navigateToProductDetail

    private val _buyRentalProductModal = MutableLiveData<Product>()
    val buyRentalProductModal : LiveData<Product> = _buyRentalProductModal


    init {
        Timber.plant(Timber.DebugTree())
        refreshMenu()
        refreshCartList()
        refreshProducts()
    }


    private fun refreshMenu() {
        _showProgressBar.value = true
        viewModelScope.launch {
            try {
                storeRepository.refreshMenu()
                _menuItems.value = storeRepository.menu()
            }catch(e : Exception){

            }finally {
                _hideProgressBar.value = true
            }
        }
    }

    private fun refreshCartList(){
        viewModelScope.launch{
            try {
                storeRepository.refreshCartList()
                _cart.value = storeRepository.getAllShoppingCart()
                _cartListProducts.value = storeRepository.cartListProducts()
                Timber.i("Success refresh cart list:")
            }catch (e: Exception){
                Timber.i("fail refresh cart list:  ${e.message}")
            }
        }
    }


    private fun refreshProducts(){

        _showProgressBar.value = true
        viewModelScope.launch {
            try {
                storeRepository.refreshProducts()
                _products.value = storeRepository.products()
            }catch (e: Exception){
                _failFetchProducts.value = "Error en la consulta de lista de productos."
                Timber.e("Fail refresh products:  ${e.message}")
            }finally {
                _hideProgressBar.value = true
            }
        }
    }


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }


    fun onProductClicked(product: Product){
        _navigateToProductDetail.value = product
    }

    fun onAddProductCartClicked(product: Product){

        if(_cartListProducts.value?.contains(product) == true){
                _productAlreadyInCart.value = true
        }else{
            _buyRentalProductModal.value = product
        }
    }

    fun onProductMenuClicked(id: Long){
        _hideMenuItems.value = true
        _showProgressBar.value = true
        viewModelScope.launch {
            _products.value  = storeRepository.filteredIdProducts(id)
            _showRecyclerViewProducts.value = true
            _hideProgressBar.value = true
            _menufilterButton.value = id
        }
    }


    fun searchViewFilter(filter : String?){

        viewModelScope.launch {
            if(filter.equals("")){
                _showMenuItems.value = true
                _hideRecyclerViewProducts.value = true
            }else{
                var list = storeRepository.products() //database.getAllProducts().value
                _products.value =list?.filter { product ->
                    product?.key_words!!.contains(filter.toString(), true)
                }
                _showRecyclerViewProducts.value = true
                _searchFilter.value = filter!!
            }
        }
    }


    fun onBuyProduct(product: Product){
        if(_cartListProducts.value?.contains(product) == false){
        viewModelScope.launch {
            storeRepository.addCartListProduct(Cart(id =null,  product_id = product.id,init_rental_date = null,end_rental_date = null,"buy"))
        }
            refreshCartList()
        }
    }




    fun onRefresh(){
        if(menufilterButton.value != null){
            _menufilterButton.value?.let {
                onProductMenuClicked(it)
            }
        }else if(searchFilter.value != null){
            searchViewFilter(_searchFilter.value)
        }
        _refreshLayout.value = true
    }


    fun onRentalProductAccepted(product : Product, firstDate : String, secondDate: String) {
    }

    fun onProductNavigated(){
        _navigateToProductDetail.value = null
    }

    fun onFailFetchProducts(){
        _failFetchProducts.value = null
    }


    fun onShowRecyclerViewProduct(){
        _showRecyclerViewProducts.value = null
    }

    fun onHideRecyclerViewProduct(){
        _hideRecyclerViewProducts.value = null
    }

    fun onMenuFiltered(){
        _menufilterButton.value = null
    }

    fun onProgressBarShowed(){
        _showProgressBar.value = null
    }

    fun onProgressBarHidden(){
        _hideProgressBar.value = null
    }

    fun onSearchFiltered(){
        _searchFilter.value = null
    }

    fun onProductAddedtoCart(){
        _productAddedToCart.value = null
    }

    fun onProductDeletedToCart(){
        _productDeletedToCart.value = null
    }

    fun onBuyRentalProductModalCleared() {
        _buyRentalProductModal.value = null
    }

    fun onRentalProduct(product : Product, firstDate : String, secondDate: String) {
        viewModelScope.launch {
            storeRepository.addCartListProduct(Cart(id =null,  product_id = product.id, init_rental_date = firstDate,end_rental_date = secondDate,"rental"))
        }
        refreshCartList()
    }

}