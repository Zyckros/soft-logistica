package com.example.softlogistica.model.product

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
     fun insert(product: Product)

    @Update
    fun update(product: Product)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     suspend fun inserAll(product : MutableList<Product?>?)

    @Query("SELECT * FROM product WHERE id = :id")
    suspend fun get(id: Long?): Product

    @Query("SELECT * FROM product")
     suspend fun getAllProducts(): MutableList<Product>

    @Query("SELECT * FROM product WHERE familyID=:id")
     suspend fun getAllProductsFromFamily(id : Long) : MutableList<Product?>

    @Query("SELECT * FROM product WHERE available=1")
    fun getAllAvailableProducts(): LiveData<List<Product>>

    @Query("SELECT * FROM product ORDER BY id DESC LIMIT 1")
     fun getLastProduct(): Product?

    @Query("DELETE FROM product WHERE id = :id")
     suspend fun deleteProduct(id: Long?)
}