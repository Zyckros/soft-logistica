package com.example.softlogistica.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.softlogistica.model.budget.Budget
import com.example.softlogistica.model.budget.BudgetDao
import com.example.softlogistica.model.cart.Cart
import com.example.softlogistica.model.cart.CartDao
import com.example.softlogistica.model.menu.MenuDao
import com.example.softlogistica.model.menu.ProductMenu
import com.example.softlogistica.model.product.Product
import com.example.softlogistica.model.product.ProductDao


@Database(entities = [Product::class,
                        ProductMenu::class,
                        Cart::class,
                     Budget::class], version = 1, exportSchema = false)
abstract class ApplicationDatabase: RoomDatabase() {

    abstract val productDatabaseDao: ProductDao
    abstract val cartListDao : CartDao
    abstract val menuDao : MenuDao
    abstract val budgetDao : BudgetDao
    
    companion object{
        @Volatile
        private var INSTANCE: ApplicationDatabase?=null

        fun getInstance(context: Context) : ApplicationDatabase {
            synchronized(this){
                var instance = INSTANCE
                if(instance == null){

                        instance = Room.databaseBuilder(
                            context.applicationContext,
                            ApplicationDatabase::class.java,
                            "database"
                        )
                            .fallbackToDestructiveMigration()
                            .build()

                    INSTANCE = instance
                }
                return instance

            }
        }
    }
}