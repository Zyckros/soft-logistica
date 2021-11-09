package com.example.softlogistica.ui.services

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.softlogistica.model.product.ProductDao

class ServiceViewModel(val database : ProductDao, application: Application) : AndroidViewModel(application) {
}