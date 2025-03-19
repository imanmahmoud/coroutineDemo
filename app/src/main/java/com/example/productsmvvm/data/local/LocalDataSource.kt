package com.example.productsmvvm.data.local

import com.example.productsmvvm.data.models.Product
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    suspend fun insertProduct(product: Product): Long
    suspend fun deleteProduct(product: Product?): Int
    suspend fun getAllProducts(): Flow<List<Product>>
}