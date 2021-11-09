package com.example.softlogistica.ui.shopping_cart

import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.softlogistica.model.cart.Cart
import com.example.softlogistica.model.product.Product
import com.example.softlogistica.ui.store.CartListAdapter


@BindingAdapter("bindingProductsCartList")
fun bindingProductsCartList(recyclerView : RecyclerView?, data: MutableList<Product?>?){

    data?.let {
        val adapter : CartListAdapter = recyclerView?.adapter as CartListAdapter
        adapter.submitList(data)
        adapter.notifyDataSetChanged()
    }

}

@BindingAdapter(value = ["product", "cart"], requireAll = false)
fun buyOrRental(textView: TextView, cart : Cart, product: Product){
    if(cart != null){
        cart.let { it ->
            if(it.buy_or_rental.equals("buy")){
                textView.text =
            }
        }
    }
}