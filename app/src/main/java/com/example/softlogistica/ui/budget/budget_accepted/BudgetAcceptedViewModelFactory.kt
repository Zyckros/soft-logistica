package com.example.softlogistica.ui.budget.budget_accepted

import android.app.Application
import android.content.SharedPreferences
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.softlogistica.model.product.ProductDao

class BudgetAcceptedViewModelFactory(private val datasource: ProductDao, private val application: Application, private var savedStateHandle: SavedStateHandle, private val sharedPrefs: SharedPreferences) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(BudgetAcceptedViewModel::class.java)){
            return BudgetAcceptedViewModel(datasource, application, savedStateHandle, sharedPrefs) as T
        }
        throw IllegalArgumentException("Unknow ViewModel class")
    }

}