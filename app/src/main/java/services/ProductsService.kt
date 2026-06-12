package com.example.candyhouse.services

import com.example.candyhouse.models.Product
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductsService {

    // Descarga los 15 dulces
    @GET("api/dulces")
    suspend fun getAllProducts(): List<Product>

    // Descarga un dulce por ID si se necesita
    @GET("api/dulces/{id}")
    suspend fun getProductById(@Path("id") id: Int): Product
}