package com.example.productcrud.view.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.productcrud.R
import com.example.productcrud.databinding.ProductItemBinding
import com.example.productcrud.entities.ProductEntity
import com.example.productcrud.utils.extensions.toCurrency
import com.example.productcrud.view.listeners.ProductItemClickListener
import java.time.LocalDate

class ProductAdapter(private val context: Context, private val listener: ProductItemClickListener) :
    ListAdapter<ProductEntity, ProductAdapter.ProductViewHolder>(ProductDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ProductItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class ProductViewHolder(private val binding: ProductItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: ProductEntity) {
            binding.apply {
                name.text = product.name
                description.text = product.description
                price.text = product.price.toCurrency
                if (LocalDate.now() >= product.dueDate) {
                    verticalLine.setBackgroundColor(context.getColor(R.color.red))
                }
                menu.setOnClickListener {
                    openPopupMenu(it, product)
                }
            }
        }
    }

    private fun openPopupMenu(v: View, product: ProductEntity) {
        val popupMenu = PopupMenu(context, v)
        popupMenu.inflate(R.menu.options_menu)
        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.edit -> listener.onEdit(product)
                R.id.delete -> listener.onDelete(product)
            }
            true
        }
        popupMenu.show()
    }

    class ProductDiffCallback : DiffUtil.ItemCallback<ProductEntity>() {
        override fun areItemsTheSame(oldItem: ProductEntity, newItem: ProductEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ProductEntity, newItem: ProductEntity): Boolean {
            return oldItem == newItem
        }
    }
}

