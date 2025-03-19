package com.example.productsmvvm.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.productsmvvm.data.models.Product
import kotlinx.coroutines.flow.Flow


@Dao
interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertProduct(product: Product): Long
    @Delete
    suspend fun deleteProduct(product: Product): Int
   @Query("SELECT * FROM products")
    /*suspend*/ fun getAllProducts(): Flow<List<Product>>


}