package com.example.candyhouse.services

import com.example.candyhouse.models.Product
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductsService {

    // Método para traer los 15 dulces en tiempo real
    @GET("api/dulces")
    suspend fun getAllProducts(): List<Product>

    // Por si en la pantalla de detalle necesitas buscar un dulce por su ID
    @GET("api/dulces/{id}")
    suspend fun getProductById(@Path("id") id: Int): Product
}