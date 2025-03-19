package com.example.productsmvvm.data.repo

import com.example.productsmvvm.data.local.LocalDataSource
import com.example.productsmvvm.data.local.ProductLocalDataSource
import com.example.productsmvvm.data.models.Product
import com.example.productsmvvm.data.remote.ProductRemoteDataSource
import com.example.productsmvvm.data.remote.RemoteDataSource
import kotlinx.coroutines.flow.Flow

class ProductRepositoryImpl private constructor(private val remoteDataSource: RemoteDataSource, private val localDataSource: LocalDataSource): ProductRepository {

    companion object {
        private var Instance: ProductRepositoryImpl? = null
        fun getInstance(remoteDataSource: RemoteDataSource, localDataSource: LocalDataSource): ProductRepository {
            return Instance ?: synchronized(this) {
                val instance = ProductRepositoryImpl(remoteDataSource, localDataSource)
                Instance = instance
                instance
            }
        }

    }

    override suspend fun getAllProducts(isOnline: Boolean) : Flow<List<Product>?> {
        if (isOnline) {
            return remoteDataSource.getAllProducts()
        }
        else {
            //return remoteDataSource.getAllProducts()
            return localDataSource.getAllProducts()
        }

    }

    override suspend fun insertProduct(product: Product): Long {
        return localDataSource.insertProduct(product)

    }
    override suspend fun deleteProduct(product: Product): Int {
        return localDataSource.deleteProduct(product)
    }


}