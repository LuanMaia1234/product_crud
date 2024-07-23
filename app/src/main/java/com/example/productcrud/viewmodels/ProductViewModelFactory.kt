package com.example.productcrud.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.productcrud.database.AppDatabase
import com.example.productcrud.repositories.ProductRepositoryImpl
import com.example.productcrud.viewmodels.form.ProductFormViewModel
import com.example.productcrud.viewmodels.list.ProductListViewModel

class ProductViewModelFactory(context: Context) : ViewModelProvider.Factory {
    private val repository = ProductRepositoryImpl(AppDatabase.getDatabase(context).getProductDao())

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(ProductListViewModel::class.java) -> {
                ProductListViewModel(repository) as T
            }

            modelClass.isAssignableFrom(ProductFormViewModel::class.java) -> {
                ProductFormViewModel(repository) as T
            }

            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}