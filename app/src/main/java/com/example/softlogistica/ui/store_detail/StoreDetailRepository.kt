package com.example.softlogistica.ui.store_detail

import com.example.softlogistica.database.ApplicationDatabase
import com.example.softlogistica.model.cart.Cart
import com.example.softlogistica.model.product.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class StoreDetailRepository(private val databaseProduct : ApplicationDatabase) {


    suspend fun getAllShoppingCart() : MutableList<Cart?> = databaseProduct.cartListDao.getAll()

    suspend fun cartListProducts() : MutableList<Product?> =databaseProduct.cartListDao.getAllCartProducts()

    suspend fun refreshCartList(){
        withContext(Dispatchers.IO) {
            // val carList = LoadData.cartListProducts
            //databaseProduct.cartListDao.insertAll(carList)
        }
    }

    suspend fun addCartListProduct(cart : Cart){
        databaseProduct.cartListDao.insert(cart)
    }
}