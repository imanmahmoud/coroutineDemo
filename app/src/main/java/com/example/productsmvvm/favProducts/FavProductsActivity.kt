package com.example.productsmvvm.favProducts

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.productsmvvm.allProducts.AllProductsViewModel
import com.example.productsmvvm.allProducts.ProductRow
import com.example.productsmvvm.data.local.ProductDatabase
import com.example.productsmvvm.data.local.ProductLocalDataSource
import com.example.productsmvvm.data.models.Product
import com.example.productsmvvm.data.remote.ProductRemoteDataSource
import com.example.productsmvvm.data.remote.RetrofitHelper
import com.example.productsmvvm.data.repo.ProductRepositoryImpl
import com.example.productsmvvm.favProducts.ui.theme.ProductsMVVMTheme

class FavProductsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val db = ProductDatabase.getInstance(context = this)

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {


            //  productsList= remember { mutableStateOf(null) }


            MainScreen(
                ViewModelProvider(
                    this,
                    FavViewModel.FavProductsFactory(
                        ProductRepositoryImpl.getInstance(
                            ProductRemoteDataSource(RetrofitHelper.service),
                            ProductLocalDataSource(db.productDao())
                        )
                    )
                )
                    .get(FavViewModel::class.java)
            )

        }


    }

    @SuppressLint("SuspiciousIndentation")
    @Composable
    fun MainScreen(viewModel :FavViewModel) {
        viewModel.getAllProducts()
        val products = viewModel.products.collectAsStateWithLifecycle()
        LazyColumn {
            items(products.value?.size?:0) {
                    currentIndex->
                ProductRow(products.value?.get(currentIndex),"delete", {viewModel.deleteProduct(
                    products.value?.get(currentIndex) ?: Product("","","","")
                )}
                )
            }
        }

    }
}

