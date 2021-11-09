package com.example.softlogistica.ui.store

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.softlogistica.databinding.ItemProductCartBinding
import com.example.softlogistica.model.product.Product

class CartListAdapter (val deleteProductCartClickListener : DeleteProductCartClickListener, val productCartClickListener : ProductCartClickListener) :  ListAdapter<Product, CartListAdapter.ViewHolder>(ProductDiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemProductCartBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position)!!, deleteProductCartClickListener, productCartClickListener)
    }


    class ViewHolder(val binding: ItemProductCartBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Product, deleteProductCartClickListener : DeleteProductCartClickListener, productCartClickListener : ProductCartClickListener) {
            binding.product = item
            binding.deleteProductCartClickListener = deleteProductCartClickListener
            binding.productCartClickListener = productCartClickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding =ItemProductCartBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    class ProductDiffCallback : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return  oldItem.id == newItem.id
        }
        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return  oldItem == newItem
        }
    }
}

class DeleteProductCartClickListener(val clickListener: (product: Product) -> Unit) {
    fun onClick(product: Product)  = clickListener(product)
}

class ProductCartClickListener(val clickListener: (product: Product) -> Unit) {
    fun onClick(product: Product)  = clickListener(product)
}
