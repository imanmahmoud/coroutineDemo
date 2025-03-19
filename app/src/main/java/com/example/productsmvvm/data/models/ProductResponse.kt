package com.example.productsmvvm.data.models

import com.google.gson.annotations.SerializedName

data class ProductResponse(
    @SerializedName("products")
    val productsList: List<Product>?
)
