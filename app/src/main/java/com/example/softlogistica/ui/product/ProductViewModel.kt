package com.example.softlogistica.ui.product

import android.app.Application
import android.content.SharedPreferences
import androidx.lifecycle.*
import com.bumptech.glide.load.model.ModelLoader
import com.example.softlogistica.model.menu.ProductMenu
import com.example.softlogistica.model.product.Product
import com.example.softlogistica.model.product.ProductDao
import comampleftlogisticaaddata.LoadData
import kotlinx.coroutines.launch
import timber.log.Timber

class ProductViewModel(val database: ProductDao, application: Application, savedStateHandle: SavedStateHandle, val sharedPrefs: SharedPreferences, val productRepository: ProductRepository) : AndroidViewModel(application) {


    private var _products = MutableLiveData<List<Product?>?>()
    val products : LiveData<List<Product?>?> = _products


    private val _menuItems = MutableLiveData<List<ProductMenu?>?>()
    val menuItems : LiveData<List<ProductMenu?>?> = _menuItems

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

    private val _failedDeleteProduct = MutableLiveData<String>()
    val failedDeleteProduct : LiveData<String> = _failedDeleteProduct

    private val _failFetchProducts = MutableLiveData<String>()
    val failFetchProducts: LiveData<String> = _failFetchProducts

    private val _deleteProduct = MutableLiveData<Product>()
    val deleteProduct: LiveData<Product> = _deleteProduct

    private val _navigateToEditProduct = MutableLiveData<Product>()
    val navigateToEditProduct: LiveData<Product> = _navigateToEditProduct

    private val _navigateToProductDetail = MutableLiveData<Product>()
    val navigateToProductDetail: LiveData<Product> = _navigateToProductDetail

    private val _navigateToNewProduct = MutableLiveData<Boolean>()
    val navigateToNewProduct: LiveData<Boolean> = _navigateToNewProduct


    init {
            refreshMenu()
            refreshProducts()
            Timber.plant(Timber.DebugTree())
    }


    private fun refreshMenu() {

        viewModelScope.launch {
            _showProgressBar.value = true
            try {
                productRepository.refreshMenu()
                _menuItems.value = LoadData.menu//productRepository.menu.value
            }catch(e : Exception){
                Timber.e("Fail refresh menu:  ${e.message}")
            }finally {
                _hideProgressBar.value = true
            }
        }
    }


    private fun refreshProducts(){
        viewModelScope.launch {
            _showProgressBar.value = true
            try {
                productRepository.refreshProducts()
                _products.value = productRepository.products()
            }catch (e: Exception){
                _failFetchProducts.value = e.message
            }finally {
                _hideProgressBar.value = true
            }
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


    override fun onCleared() {
        super.onCleared()

    }


     fun deleteProduct(id: Long?){
        viewModelScope.launch {
            try{
                productRepository.deleteProduct(id)
                /** Filtro para la lista de productos sin tener que llamar a refreshProduct, pero cuando este en produccion habra que eniar la solicitud de borrado al backend web
                _products.value = _products.value?.filter { product ->
                product?.id != id
                }*/
                refreshProducts()
            }catch(e : Exception){
                _failedDeleteProduct.value = e.message
            }
        }
     }


    fun onProductMenuClicked(id: Long) {
        _hideMenuItems.value = true
        _showProgressBar.value = true
        viewModelScope.launch {
            _products.value = productRepository.filteredIdProducts(id)
        }

        _showRecyclerViewProducts.value = true
        _hideProgressBar.value = true
        _menufilterButton.value = id
    }


    fun onNewProductClicked(){
        _navigateToNewProduct.value = true
    }


    fun searchViewFilter(filter : String?) {
        viewModelScope.launch {
            if(filter.equals("")){
                _showMenuItems.value = true
                _hideRecyclerViewProducts.value = true
            }else{
                var list = productRepository.products() //database.getAllProducts().value
                _products.value =list?.filter { product ->
                    product?.key_words!!.contains(filter.toString(), true)
                }
                _showRecyclerViewProducts.value = true
                _searchFilter.value = filter!!
            }
        }
        }


    fun onEditProductClicked(product: Product) {
        _navigateToEditProduct.value = product
    }

    fun onDeleteProductClicked(product: Product) {
        _deleteProduct.value = product
    }


    fun onEditProductNavigated() {
        _navigateToEditProduct.value = null
    }

    fun onProductDeleted() {
        _deleteProduct.value = null
    }
    fun onProductNavigated(){
        _navigateToProductDetail.value = null
    }

    fun onFailFetchProducts(){
        _failFetchProducts.value = null
    }

    fun onNewProductNavigated(){
        _navigateToNewProduct.value = null
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

    fun onProductClicked(product: Product){
        _navigateToProductDetail.value = product
    }
}
