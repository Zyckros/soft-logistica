package com.example.softlogistica.model.budget

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy

@Dao
interface BudgetDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(list : Budget)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun inserAll(list : MutableList<Budget?>?)

//    @Query("SELECT * from budget")
//    suspend fun getAll() : LiveData<List<Budget?>?>

}