package com.example.productcrud.repositories

import com.example.productcrud.database.ProductDao
import com.example.productcrud.entities.ProductEntity

class ProductRepositoryImpl(private val productDao: ProductDao) : ProductRepository {
    override fun getAll(): List<ProductEntity> {
        return productDao.getAll()
    }

    override fun insert(product: ProductEntity): Boolean {
        return productDao.insert(product) > 0
    }

    override fun update(product: ProductEntity): Boolean {
        return productDao.update(product) > 0
    }

    override fun delete(product: ProductEntity): Boolean {
        return productDao.delete(product) > 0
    }
}