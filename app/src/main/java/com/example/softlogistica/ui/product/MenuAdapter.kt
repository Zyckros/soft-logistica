package com.example.softlogistica.ui.product

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.softlogistica.databinding.ItemMenuProductBinding
import com.example.softlogistica.model.menu.ProductMenu



class MenuAdapter(val clickListener: MenuListener) : ListAdapter<ProductMenu, MenuAdapter.ViewHolder>(MenuDiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        
        return ViewHolder(ItemMenuProductBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position)!!, clickListener)
    }


    class ViewHolder(val binding: ItemMenuProductBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ProductMenu, clickListener: MenuListener) {
            binding.clickListener = clickListener
            binding.menu = item
            binding.executePendingBindings()
        }
        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemMenuProductBinding.inflate(layoutInflater, parent, false)
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


class MenuListener(val clickListener:(id: Long) -> Unit) {
    fun onClick(id: Long)  = clickListener(id)
}








