package com.example.softlogistica.model.cart

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.softlogistica.model.product.Product

@Dao
interface CartDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(list : Cart)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list : MutableList<Cart?>?)

    @Query("SELECT product.* FROM product INNER JOIN cart ON product.id = cart.product_id")
    suspend fun getAllCartProducts() : MutableList<Product?>

    @Query( "SELECT * FROM cart")
    suspend fun getAll() : MutableList<Cart?>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(element : MutableList<Cart>)

    @Query("DELETE FROM cart WHERE product_id = :id")
    suspend fun removeProductCart(id: Long?)

    @Query("DELETE FROM cart")
    suspend fun emptyShoppingCart()
}