package com.example.softlogistica.ui.store

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.softlogistica.databinding.ItemMenuProductStoreBinding
import com.example.softlogistica.model.menu.ProductMenu

class MenuStoreAdapter (val clickListener: MenuStoreListener) : ListAdapter<ProductMenu, MenuStoreAdapter.ViewHolder>(MenuDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(ItemMenuProductStoreBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position)!!, clickListener)
    }

    class ViewHolder(val binding: ItemMenuProductStoreBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ProductMenu, clickListener: MenuStoreListener) {
            binding.clickListener = clickListener
            binding.menu = item
            binding.executePendingBindings()
        }
        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemMenuProductStoreBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    class MenuDiffCallback : DiffUtil.ItemCallback<ProductMenu>() {
        override fun areItemsTheSame(oldItem: ProductMenu, newItem: ProductMenu): Boolean {
            return oldItem.id == newItem.id
        }
        override fun areContentsTheSame(oldItem: ProductMenu, newItem: ProductMenu): Boolean {
            return oldItem == newItem
        }
    }

}

class MenuStoreListener(val clickListener:(id: Long) -> Unit) {
    fun onClick(id: Long)  = clickListener(id)
}