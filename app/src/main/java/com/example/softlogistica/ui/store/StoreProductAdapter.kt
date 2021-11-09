package com.example.softlogistica.ui.store

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.softlogistica.databinding.ItemProductStoreBinding
import com.example.softlogistica.model.product.Product

class StoreProductAdapter (val clickListener: StoreProductListener, val cartClickListenerAddProduct: AddProductStoreCartClickListener) :  ListAdapter<Product, StoreProductAdapter.ViewHolder>(ProductDiffCallback()) {


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemProductStoreBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position)!!, clickListener, cartClickListenerAddProduct)
    }


    class ViewHolder(val binding: ItemProductStoreBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Product, clickListener: StoreProductListener, cartClickListenerAddProduct: AddProductStoreCartClickListener) {
            binding.clickListener = clickListener
            binding.addProductStoreCartClickListener = cartClickListenerAddProduct
            binding.product = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemProductStoreBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    class ProductDiffCallback : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }
        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }
    }
}


class StoreProductListener(val clickListener:(product: Product) -> Unit) {
    fun onClick(product: Product)  = clickListener(product)
}

class AddProductStoreCartClickListener(val clickListener: (product: Product) -> Unit) {
    fun onClick(product: Product)  = clickListener(product)
}
