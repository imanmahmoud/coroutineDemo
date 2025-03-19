package com.example.productsmvvm

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import com.example.productsmvvm.allProducts.AllProductsActivity
import com.example.productsmvvm.favProducts.FavProductsActivity
import com.example.productsmvvm.ui.theme.ProductsMVVMTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // enableEdgeToEdge()
        setContent {
            MainScreen()
        }
    }
    @Composable
    fun MainScreen() {
        Column (modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally){
            Button(onClick = {
                startActivity(Intent(this@MainActivity, AllProductsActivity::class.java))

            }, modifier = Modifier.padding(10.dp)){
                Text(text = "get all products")
            }
            Spacer(modifier = Modifier.padding(10.dp))
            Button(onClick = {
                startActivity(Intent(this@MainActivity, FavProductsActivity::class.java))

            }, modifier = Modifier.padding(10.dp)){
                Text(text = "get fav product")
            }


        }
    }
}



