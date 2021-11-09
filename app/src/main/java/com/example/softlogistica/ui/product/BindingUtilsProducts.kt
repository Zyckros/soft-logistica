package com.example.softlogistica.ui.product

import android.annotation.SuppressLint
import android.view.View
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.softlogistica.R
import com.example.softlogistica.model.menu.ProductMenu
import com.example.softlogistica.model.product.Product




@SuppressLint("NotifyDataSetChanged")
@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView?, data: MutableList<Product?>?) {
    val adapter = recyclerView?.adapter as ProductAdapter
    if(data != null) {
        data?.let {data ->
            adapter.submitList(data)
        }
    }
}


@BindingAdapter("listDataMenu")
fun bindRecyclerViewMenu(recyclerView: RecyclerView?, data: List<ProductMenu?>?) {
    val adapter = recyclerView?.adapter as MenuAdapter
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