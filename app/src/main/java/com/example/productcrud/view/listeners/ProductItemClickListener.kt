package com.example.productcrud.view.listeners

import com.example.productcrud.entities.ProductEntity

interface ProductItemClickListener {
    fun onEdit(productEntity: ProductEntity)

    fun onDelete(productEntity: ProductEntity)
}