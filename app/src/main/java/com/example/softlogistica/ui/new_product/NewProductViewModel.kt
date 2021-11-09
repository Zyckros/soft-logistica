package com.example.softlogistica.ui.new_product

import android.app.Application
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.*
import com.example.softlogistica.model.product.Product
import com.example.softlogistica.model.product.ProductDao
import com.example.softlogistica.network.ProductApiService
import com.example.softlogistica.network.Service
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class NewProductViewModel(private val dataSource: ProductDao, private val application: Application) : ViewModel(){


        var _newProduct = MutableLiveData<Product>()
        var newProduct = MutableLiveData<Product>()

        


        override fun onCleared() {
                super.onCleared()
                Timber.i("NewProductViewModel Destroyed!!")
        }


        var product = MutableLiveData<Product?>()
        private var viewModelJob = Job()
        private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

        init {
               // initializeProduct()
        }


        private fun initializeProduct() {
                viewModelScope.launch {
                        product.value = getProductFromDatabase()
                }
        }

        private suspend fun getProductFromDatabase(): Product? {
                return  dataSource.getLastProduct()
        }

        fun saveProduct(){
               uiScope.launch {
//                       val product = Product(1, internalCode.value, descritpion.value,broadDescritpion.value, buyDate.value, dischargeDate.value, brand.value, model.value, licence.value,
//                               serialNumber.value, buyPrice.value, loadmaxM3.value?.toDouble(), loadmaxTones.value?.toDouble(), high.value?.toDouble(), width.value?.toDouble(), depth.value?.toDouble(), numberChassis.value,frequenzyHz.value,
//                               intensityAmp.value,lengthmm.value?.toDouble(),licenceTrailer.value,engine.value, netWeightKg.value?.toDouble(),tareWeightKg.value?.toDouble(),numberAxle.value,potencyKw.value?.toDouble(),voltage.value?.toDouble(),
//                               rentalPriceDay.value?.toDouble(),rentalPriceHour.value?.toDouble(), rentalPriceKilometer.value?.toDouble(),rentalPriceMonth.value?.toDouble(),journeyPrice.value?.toDouble(),salePrice.value?.toDouble(),photoProduct.value,
//                               photoPiecesProduct.value)
//
//                       Service.retrofitService.saveProduct(product)


                }
        }

        private suspend fun insert(product: Product) {dataSource.insert(product)}


//        @JvmName("setImageProduct1")
//        fun setImageProduct(uri: MutableLiveData<String>){
//                photoProduct = uri
//        }

}

