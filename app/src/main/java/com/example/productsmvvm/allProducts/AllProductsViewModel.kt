package com.example.productsmvvm.allProducts

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.productsmvvm.data.models.Product
import com.example.productsmvvm.data.models.Response
import com.example.productsmvvm.data.repo.ProductRepository
import com.example.productsmvvm.data.repo.ProductRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class AllProductsViewModel(private val repo: ProductRepository) : ViewModel() {
    /* private val _products: MutableLiveData<List<Product>> = MutableLiveData()
     val products: MutableLiveData<List<Product>> = _products

     private val _message: MutableLiveData<String> = MutableLiveData()
     val message: MutableLiveData<String> = _message*/


    private var _products = MutableStateFlow<Response>(Response.Loading)
    val products = _products.asStateFlow()

    private val _message = MutableSharedFlow<String>()
    val message = _message.asSharedFlow()


    fun getAllProducts() {
        Log.i("TAG", "getAllProducts:1 ")
        viewModelScope.launch {
            Log.i("TAG", "getAllProducts:2 ")
            try {
                val result = repo.getAllProducts(true)
                result
                    .catch { e ->
                        _products.value = Response.Failure(e)
                        _message.emit("Error from Api: ${e.message}")
                    }
                    .collect {
                        _products.value = Response.Success(it ?: emptyList())
                    }
            } catch (e: Exception) {
                _products.value = Response.Failure(e)
                _message.emit(e.message ?: "Unknown error")
            }


        }
    }


    /*fun getAllProducts(isOnline: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = repo.getAllProducts(isOnline)
                if (result != null) {
                    val list: List<Product> = result
                    _products.postValue(list)
                }
                else {
                    _message.postValue("please try again later")
                }

            } catch (e: Exception) {
                _message.postValue(e.message)
            }
        }

    }*/


    /* fun getAllProducts(){
         Log.i("TAG", "getAllProducts: 1")
         viewModelScope.launch {
             Log.i("TAG", "getAllProducts: 2")
             val result = repo.getAllProducts(true)
             result.collect {
                 _products.value = it
             }

         }
     }*/

    fun addToFavorite(product: Product?) {
        if (product != null) {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    val result = repo.insertProduct(product)
                    if (result > 0) {
                        _message.emit("Product added to favorites")
                    } else {

                        _message.emit("couldn't add to favorites")
                    }
                } catch (e: Exception) {

                    _message.emit(e.message ?: "Unknown error")
                }
            }
        }
        /*else{
            _message.emit("couldn't add to favorites,Missing Data")
        }*/
    }

    class AllProductsFactory(private val repo: ProductRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return AllProductsViewModel(repo) as T
        }
    }


}