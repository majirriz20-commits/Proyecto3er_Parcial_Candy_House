package com.example.candyhouse.services

import com.example.candyhouse.services.ProductsService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://apicandyhouse-production.up.railway.app/"
    val apiService: CandyApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CandyApiService::class.java)
    }
}