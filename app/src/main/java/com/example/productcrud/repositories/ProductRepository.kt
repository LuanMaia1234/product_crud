package com.example.productcrud.repositories

import com.example.productcrud.entities.ProductEntity

interface ProductRepository {
    fun getAll(): List<ProductEntity>

    fun insert(product: ProductEntity): Boolean

    fun update(product: ProductEntity): Boolean

    fun delete(product: ProductEntity): Boolean
}