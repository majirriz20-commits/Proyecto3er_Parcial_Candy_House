package com.example.candyhouse.services

import com.example.candyhouse.services.ProductsService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "http://10.0.2.2:3000/" // emulador: 10.0.2.2 | dispositivo físico: IP de tu compu
    val apiService: CandyApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CandyApiService::class.java)
    }
}