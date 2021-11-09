package com.example.softlogistica.model.menu

import androidx.room.*

@Dao
interface MenuDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(menu: ProductMenu)

    @Update
    fun update(menu : ProductMenu)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun inserAll(menu: List<ProductMenu>)

    @Query("SELECT * FROM product_menu WHERE id = :id")
    suspend fun get(id: Long): ProductMenu

    @Query("SELECT * FROM product_menu ORDER BY id DESC")
    suspend fun getAllMenus(): MutableList<ProductMenu?>

    @Query("SELECT * FROM product_menu ORDER BY id DESC LIMIT 1")
    fun getLastMenu(): ProductMenu?

    @Query("DELETE FROM product_menu WHERE id = :id")
    suspend fun deleteMenu(id: Long)
}