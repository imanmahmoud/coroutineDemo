package com.example.productsmvvm.data.remote

import com.example.productsmvvm.data.models.ProductResponse
import retrofit2.Response
import retrofit2.http.GET

interface ProductApiService {
    @GET("products")
    suspend fun getAllProducts(): Response<ProductResponse/*?*/>/*?*/

}