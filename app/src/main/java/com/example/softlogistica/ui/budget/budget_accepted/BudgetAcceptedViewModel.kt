package com.example.softlogistica.ui.budget.budget_accepted

import android.app.Application
import android.content.SharedPreferences
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.softlogistica.model.product.ProductDao

class BudgetAcceptedViewModel(private val datasource: ProductDao, private val application: Application, private var savedStateHandle: SavedStateHandle, private val sharedPrefs: SharedPreferences) : ViewModel() {
}