package com.example.productsmvvm.data.remote

import com.example.productsmvvm.data.models.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Retrofit



class ProductRemoteDataSource(private val productApiService: ProductApiService) : RemoteDataSource {
    override /*suspend*/ fun getAllProducts(): Flow<List<Product>> = flow {
       productApiService.getAllProducts().body()?.productsList?.let { emit(it) }/*?: emit(emptyList())*/

    }
}
