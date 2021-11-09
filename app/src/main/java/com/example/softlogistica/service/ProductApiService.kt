
package com.example.softlogistica.network

import androidx.lifecycle.LiveData
import com.example.softlogistica.model.product.Product
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path


/*  "https://android-kotlin-fun-mars-server.appspot.com"    --> anroid develpers url
    "https://agile-crag-08380.herokuapp.com/api/"           --> heroku Cesar
*/

private const val BASE_URL = "http://agile-crag-08380.herokuapp.com/api/"

    val okHttpClient = OkHttpClient.Builder()
        .followRedirects(false)
        .build()

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .client(okHttpClient)
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface ProductApiService {
    /*@POST("/auth/login/")
    fun getAuth(): Call<String>*/

    @GET("products")
    suspend fun getAllProducts(/*@Header("Authorization") token: String*/): LiveData<MutableList<Product?>?>

    @POST("product")
    suspend fun saveProduct(@Body product: Product) : Callback<String>

    @GET("product/{id}")
    fun get(@Path("id")id: Long) : Product


}




interface StoreApiService {
    /*@POST("/auth/login/")
    fun getAuth(): Call<String>*/

    @GET("products")
    suspend fun getCartListProducts(/*@Header("Authorization") token: String*/): LiveData<MutableList<Product?>?>

    @POST("product")
    suspend fun saveProduct(@Body product: Product) : Callback<String>


}


object Service {
    val retrofitService : ProductApiService by lazy {
        retrofit.create(ProductApiService::class.java)
    }
}