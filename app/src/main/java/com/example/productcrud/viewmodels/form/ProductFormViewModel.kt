package com.example.productcrud.viewmodels.form

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.productcrud.entities.ProductEntity
import com.example.productcrud.repositories.ProductRepository
import com.example.productcrud.utils.extensions.toFormattedBigDecimal
import com.example.productcrud.utils.extensions.toLocalDateByDayMonthYear

class ProductFormViewModel(private val repository: ProductRepository) : ViewModel() {

    private val _state = MutableLiveData<ProductFormState>()
    val state: LiveData<ProductFormState> = _state

    init {
        _state.value = ProductFormState()
    }

    fun setEditProduct(product: ProductEntity) {
        _state.value = _state.value!!.copy(editProduct = product)
    }

    fun save(
        name: String,
        description: String,
        price: String,
        dueDate: String,
    ) {
        if (_state.value!!.editProduct != null) {
            val updatedProduct = _state.value!!.editProduct!!.copy(
                name = name,
                description = description,
                price = price.toFormattedBigDecimal,
                dueDate = dueDate.toLocalDateByDayMonthYear,
            )
            val updated = repository.update(updatedProduct)
            _state.value = _state.value!!.copy(saved = updated)
        } else {
            val newProduct = ProductEntity(
                null,
                name,
                description,
                price.toFormattedBigDecimal,
                dueDate.toLocalDateByDayMonthYear,
            )
            val inserted = repository.insert(newProduct)
            _state.value = _state.value!!.copy(saved = inserted)
        }
    }

    data class ProductFormState(
        var editProduct: ProductEntity? = null,
        val saved: Boolean = false,
    )
}