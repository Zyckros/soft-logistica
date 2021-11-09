package com.example.softlogistica.ui.qr

import com.example.softlogistica.database.ApplicationDatabase
import com.example.softlogistica.model.product.Product

class BarCodeRepository(private val databaseProduct : ApplicationDatabase) {
    suspend fun product(id : Long?) : Product = databaseProduct.productDatabaseDao.get(id)
}