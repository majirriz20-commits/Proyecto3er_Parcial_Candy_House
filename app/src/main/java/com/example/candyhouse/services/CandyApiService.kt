package com.example.candyhouse.services

import com.example.candyhouse.models.Product
import retrofit2.http.GET
import retrofit2.http.Path

interface CandyApiService {
    @GET("api/dulces")
    suspend fun getDulces(): List<Product>

    @GET("api/dulces/{id}")
    suspend fun getDulcePorId(@Path("id") id: Int): Product
}