package com.example.productsmvvm.data.models

sealed class Response {
    data object Loading : Response()
    data class Success(val data: List<Product>) : Response()
    data class Failure(val errorMessage: Throwable) : Response()


}