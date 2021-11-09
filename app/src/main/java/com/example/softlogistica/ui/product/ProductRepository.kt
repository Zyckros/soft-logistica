package com.example.softlogistica.ui.product

import com.example.softlogistica.database.ApplicationDatabase
import com.example.softlogistica.model.menu.ProductMenu
import com.example.softlogistica.model.product.Product
import comampleftlogisticaaddata.LoadData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProductRepository(private val databaseProduct : ApplicationDatabase) {

    suspend fun  products() : List<Product> = databaseProduct.productDatabaseDao.getAllProducts()
    suspend fun  menu() : MutableList<ProductMenu?> = databaseProduct.menuDao.getAllMenus()



    suspend fun deleteProduct(id : Long?){
        withContext(Dispatchers.IO){
            databaseProduct.productDatabaseDao.deleteProduct(id)
        }
    }

    suspend fun filteredIdProducts(id : Long) : MutableList<Product?> {
        return databaseProduct.productDatabaseDao.getAllProductsFromFamily(id)
    }


    suspend fun refreshProducts() {
        withContext(Dispatchers.IO) {
            val products = LoadData.products //Service.retrofitService.getAllProducts().value
            databaseProduct.productDatabaseDao.inserAll(products)
        }
    }
        suspend  fun refreshMenu() {
            withContext(Dispatchers.IO) {
                val menu = LoadData.menu //Service.retrofitService.getAllProducts().value
                databaseProduct.menuDao.inserAll(menu)
            }
        }


    }