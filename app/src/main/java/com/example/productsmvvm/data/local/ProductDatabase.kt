package com.example.productsmvvm.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.productsmvvm.data.models.Product


@Database(entities = [Product::class], version = 1)
abstract class ProductDatabase: RoomDatabase() {
    abstract fun productDao(): ProductDao
    companion object {
        @Volatile
        private var INSTANCE: ProductDatabase?= null
        fun getInstance(context: Context):ProductDatabase{
            return INSTANCE?: synchronized(this){
                val instance: ProductDatabase= Room.databaseBuilder(context, ProductDatabase::class.java, "product_db").build()
                INSTANCE=instance
                instance
            }
        }
    }

    }
