package com.example.productsmvvm.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
@Entity(tableName = "products")
data class Product(
    @PrimaryKey
    var title: String,
    var price: String,
    var description: String,

    @field:SerializedName(
        "thumbnail"
    ) var image: String
)