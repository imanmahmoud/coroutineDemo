package com.example.productsmvvm.allProducts

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.productsmvvm.data.local.ProductDatabase
import com.example.productsmvvm.data.local.ProductLocalDataSource
import com.example.productsmvvm.data.models.Product
import com.example.productsmvvm.data.models.Response
import com.example.productsmvvm.data.remote.ProductRemoteDataSource
import com.example.productsmvvm.data.remote.RemoteDataSource
import com.example.productsmvvm.data.remote.RetrofitHelper
import com.example.productsmvvm.data.repo.ProductRepositoryImpl

class AllProductsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val db = ProductDatabase.getInstance(context = this)

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {


            //  productsList= remember { mutableStateOf(null) }


            MainScreen(
                ViewModelProvider(
                    this,
                    AllProductsViewModel.AllProductsFactory(
                        ProductRepositoryImpl.getInstance(
                            ProductRemoteDataSource(RetrofitHelper.service),
                            ProductLocalDataSource(db.productDao())
                        )
                    )
                )
                    .get(AllProductsViewModel::class.java)
            )

        }


    }


    // @SuppressLint("SuspiciousIndentation")
    @SuppressLint("SuspiciousIndentation")
    @Composable
    fun MainScreen(viewModel: AllProductsViewModel) {



       viewModel.getAllProducts()
        // val products = viewModel.products.collectAsStateWithLifecycle()
        val uiState by viewModel.products.collectAsStateWithLifecycle()



        when (uiState) {
            is Response.Loading -> {
                Box(modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize()) {
                    CircularProgressIndicator()
                }
            }

            is Response.Success -> {
                val products = uiState as Response.Success
                LazyColumn {
                    items(products.data.size) { currentIndex ->
                        ProductRow(products.data[currentIndex], "favourite", {
                            viewModel.addToFavorite(
                                products.data[currentIndex]
                            )
                        }
                        )
                    }
                }

            }

            is Response.Failure -> {
                Toast.makeText(
                    this,
                    (uiState as Response.Failure).errorMessage.message,
                    Toast.LENGTH_SHORT
                ).show()
            }


        }


    }

}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ProductRow(product: Product?, actionName: String, action: (Product?) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(color = Color.White)
    ) {
        GlideImage(
            model = product?.image, contentDescription = "", modifier = Modifier.size(100.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(verticalArrangement = Arrangement.Center, modifier = Modifier.padding(16.dp)) {
            Text(text = product?.title ?: "")

            Text(
                text = product?.price ?: "",
                modifier = Modifier.padding(vertical = 16.dp)
            )
            Spacer(Modifier.weight(1f))
            Button(
                onClick = { action.invoke(product) },
                Modifier
                    .padding(top = 32.dp)
                    .width(116.dp)
            ) {
                Text(actionName)
            }

        }


    }
}

