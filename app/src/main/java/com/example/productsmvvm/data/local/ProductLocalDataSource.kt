package com.example.productsmvvm.data.local

import com.example.productsmvvm.data.models.Product
import kotlinx.coroutines.flow.Flow

class ProductLocalDataSource(private val productDao: ProductDao): LocalDataSource {
    override suspend fun insertProduct(product: Product): Long {
        return productDao.insertProduct(product)
    }
    override suspend fun deleteProduct(product: Product?): Int {
        if (product != null) {
           return productDao.deleteProduct(product)
        }
        else{
            return -1
        }
    }
    override suspend fun getAllProducts(): Flow<List<Product>> {
        return productDao.getAllProducts()
    }

}