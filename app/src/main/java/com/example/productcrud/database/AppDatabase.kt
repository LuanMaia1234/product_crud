package com.example.productcrud.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.productcrud.entities.ProductEntity
import com.example.productcrud.utils.Converters
import com.example.productcrud.utils.constants.Constants

@Database(entities = [ProductEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getProductDao(): ProductDao

    companion object {
        private lateinit var instance: AppDatabase

        fun getDatabase(context: Context): AppDatabase {
            if (!::instance.isInitialized) {
                synchronized(this) {
                    instance =
                        Room.databaseBuilder(
                            context,
                            AppDatabase::class.java,
                            Constants.DATABASE.NAME
                        )
                            .allowMainThreadQueries().build()
                }
            }
            return instance
        }
    }
}