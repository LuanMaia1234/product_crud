package com.example.productcrud.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.productcrud.entities.ProductEntity

@Dao
interface ProductDao {

    @Query("SELECT * FROM PRODUCTS ORDER BY id DESC")
    fun getAll(): List<ProductEntity>

    @Insert
    fun insert(product: ProductEntity): Long

    @Update
    fun update(product: ProductEntity): Int

    @Delete
    fun delete(product: ProductEntity): Int
}