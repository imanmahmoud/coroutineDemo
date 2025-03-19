package com.example.productsmvvm.data.repo

import com.example.productsmvvm.data.models.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    suspend fun getAllProducts(isOnline: Boolean): Flow<List<Product>?>
    suspend fun insertProduct(product: Product): Long
    suspend fun deleteProduct(product: Product): Int


}