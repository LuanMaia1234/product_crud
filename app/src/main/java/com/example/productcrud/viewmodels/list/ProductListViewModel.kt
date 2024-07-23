package com.example.productcrud.viewmodels.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.productcrud.entities.ProductEntity
import com.example.productcrud.repositories.ProductRepository

class ProductListViewModel(private val repository: ProductRepository) : ViewModel() {

    private val _products = MutableLiveData<List<ProductEntity>>(emptyList())
    val products: LiveData<List<ProductEntity>> = _products

    fun getAll() {
        _products.value = repository.getAll()
    }

    fun delete(product: ProductEntity) {
        val deleted = repository.delete(product)
        if (deleted) {
            val newList = _products.value!!.toMutableList()
            newList.remove(product)
            _products.value = newList.toList()
        }
    }
}