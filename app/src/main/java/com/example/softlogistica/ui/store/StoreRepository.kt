package com.example.softlogistica.ui.store

import com.example.softlogistica.database.ApplicationDatabase
import com.example.softlogistica.model.cart.Cart
import com.example.softlogistica.model.menu.ProductMenu
import com.example.softlogistica.model.product.Product
import comampleftlogisticaaddata.LoadData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class StoreRepository(private val databaseProduct : ApplicationDatabase) {

    suspend fun  products() : MutableList<Product> = databaseProduct.productDatabaseDao.getAllProducts()
    suspend fun menu() : MutableList<ProductMenu?> = databaseProduct.menuDao.getAllMenus()
    suspend fun getAllShoppingCart() : MutableList<Cart?> = databaseProduct.cartListDao.getAll()
    suspend fun cartListProducts() : MutableList<Product?> =databaseProduct.cartListDao.getAllCartProducts()


    suspend fun removeCartListProduct(id : Long?){
        withContext(Dispatchers.IO){
            databaseProduct.cartListDao.removeProductCart(id)
        }
    }

    suspend fun addCartListProduct(cart : Cart){
        databaseProduct.cartListDao.insert(cart)
    }

    suspend fun filteredIdProducts(id : Long) : MutableList<Product?> {
        return databaseProduct.productDatabaseDao.getAllProductsFromFamily(id)
    }


    suspend fun refreshProducts() {
        withContext(Dispatchers.IO) {
            val products =  LoadData.products //Service.retrofitService.getAllProducts().value
            databaseProduct.productDatabaseDao.inserAll(products)
        }
    }

    suspend fun refreshCartList(){
        withContext(Dispatchers.IO) {
               // val carList = LoadData.cartListProducts
                //databaseProduct.cartListDao.insertAll(carList)
        }
    }

    suspend fun refreshMenu() {
        try {
            val menus = LoadData.menu
            databaseProduct.menuDao.inserAll(menus)
            Timber.i("Insertion menus on database correct")
        }catch (e : Exception){
            Timber.e("Failed to insert all menus on database: ${e.message}")
        }
    }


    init {
        Timber.plant(Timber.DebugTree())
    }
}