package com.example.productsmvvm.favProducts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.productsmvvm.data.models.Product
import com.example.productsmvvm.data.repo.ProductRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class FavViewModel(private val repo: ProductRepository) : ViewModel() {
    private var _products = MutableStateFlow<List<Product>?>(emptyList())
    val products = _products.asStateFlow()

    private val _message=MutableSharedFlow<String>()
    val message= _message.asSharedFlow()

  /*  private val _message: MutableLiveData<String> = MutableLiveData()
    val message: MutableLiveData<String> = _message*/


    fun deleteProduct(product: Product) {
        viewModelScope.launch {
            repo.deleteProduct(product)
            // getAllProducts()

        }
    }

    fun getAllProducts() {
        viewModelScope.launch {
            try {
                val result = repo.getAllProducts(false)
                result
                    .catch { e ->
                        _message.emit(e.message ?: "Unknown error")
                    }
                    .collect {
                        _products.value = it
                    }
            } catch (e: Exception) {
                _message.emit(e.message ?: "Unknown error")
            }


        }
    }


    /*  fun getAllProducts(){
          viewModelScope.launch {
              val result = repo.getAllProducts(false)
              result.collect {
                  _products.value = it
              }

          }
      }*/
    /*  fun getAllProducts(){
          viewModelScope.launch {
              val result = repo.getAllProducts(false)
              if (result != null) {
                  val list: List<Product> = result
                  _products.postValue(list)
              } else {
                  _message.postValue("please try again later")
              }
          }
      }*/

    class FavProductsFactory(private val repo: ProductRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return FavViewModel(repo) as T
        }
    }

}