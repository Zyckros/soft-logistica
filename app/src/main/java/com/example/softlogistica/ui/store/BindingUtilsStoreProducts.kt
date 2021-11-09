package com.example.softlogistica.ui.store

import android.annotation.SuppressLint
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.softlogistica.R
import com.example.softlogistica.model.cart.Cart
import com.example.softlogistica.model.menu.ProductMenu
import com.example.softlogistica.model.product.Product


@BindingAdapter("totalPrice")
fun totalPrice(textView: TextView, list : LiveData<MutableList<Product?>?>){

}


@BindingAdapter("showBuyOrRental")
fun showBuyOrRental(textView: TextView, cart : Cart){
    cart?.let {
        if (it.buy_or_rental?.contains("buy") != null){
            textView.text = "Comprar"
        }else{
            //textView.text = "Alquilar : ${it.init_rental_date} - ${it.end_rental_date}"
        }
    }
}

@SuppressLint("NotifyDataSetChanged")
@BindingAdapter("listDataProductsStore")
fun bindRecyclerView(recyclerView: RecyclerView?, data: MutableList<Product?>?) {
    val adapter = recyclerView?.adapter as StoreProductAdapter
    if(data != null) {
        data?.let {data ->
            adapter.submitList(data)
        }
    }
}



@BindingAdapter("listDataMenuStore")
fun bindRecyclerViewMenu(recyclerView: RecyclerView?, data: List<ProductMenu?>?) {
    val adapter = recyclerView?.adapter as MenuStoreAdapter
    adapter.submitList(data)
}


@BindingAdapter("imageUrl")
fun imageUrl(imgView: ImageView, product: Product?) {

    if(product == null){
     imgView.setImageResource(R.drawable.ic_menu_gallery)
        imgView.visibility = View.GONE
    }else {
        product.let {
            val imgUri =  product.photo_product?.toUri()?.buildUpon()?.scheme("https")?.build()
            Glide.with(imgView.context)
                .load(imgUri)
                .into(imgView)
        }
    }
}

@BindingAdapter("imageSrc")
fun imageSrc(imgView: ImageView, src: Int) {

    if(src == null){
        imgView.setImageResource(R.drawable.ic_menu_gallery)
        imgView.visibility = View.GONE
    }else {
        imgView.setBackgroundResource(src)
    }
}

