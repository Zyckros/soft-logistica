package com.example.softlogistica.ui.product

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.softlogistica.databinding.ItemProductViewBinding
import com.example.softlogistica.model.product.Product


class ProductAdapter(val clickListener: ProductListener, val deleteClickListener: DeleteClickListener, val editClickListener: EditClickListener) : ListAdapter<Product, ProductAdapter.ViewHolder>(ProductDiffCallback()) {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemProductViewBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position)!!, clickListener, deleteClickListener, editClickListener)
    }


    class ViewHolder(val binding: ItemProductViewBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Product, clickListener: ProductListener, deleteClickListener: DeleteClickListener, editClickListener: EditClickListener) {
            binding.clickListener = clickListener
            binding.deleteCliclListener = deleteClickListener
            binding.editClickListener = editClickListener
            binding.product = item
            binding.executePendingBindings()
        }
        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemProductViewBinding.inflate(layoutInflater, parent, false)
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


class ProductListener(val clickListener:(product: Product) -> Unit) {
    fun onClick(product: Product)  = clickListener(product)
}

class DeleteClickListener(val clickListener: (product: Product) -> Unit) {
    fun onClick(product: Product)  = clickListener(product)
}
class EditClickListener(val clickListener: (product: Product) -> Unit) {
    fun onClick(product: Product)  = clickListener(product)
}










