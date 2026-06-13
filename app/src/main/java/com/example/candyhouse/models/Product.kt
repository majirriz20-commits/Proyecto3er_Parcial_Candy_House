package com.example.candyhouse.models

import com.google.gson.annotations.SerializedName

data class Product(
    val id: Int,
    val nombre: String,
    val precio: Double,
    val estado: String,
    @SerializedName("imageUrl") val imageUrl: String,

    val proveedor: String,
    val existencia: String,
    val pasillo: String,
    val fechaCaducidad: String
)