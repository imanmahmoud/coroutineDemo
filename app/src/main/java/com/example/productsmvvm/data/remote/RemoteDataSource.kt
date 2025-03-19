package com.example.productsmvvm.data.remote

import com.example.productsmvvm.data.models.Product
import kotlinx.coroutines.flow.Flow

interface RemoteDataSource {
    /*suspend*/ fun getAllProducts(): Flow<List<Product>?>

}