package com.example.weather.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object RetrofitClient {

    const val BASE_URL = "https://api.openweathermap.org/"

    var client = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    var apiRequest: ApiRequest = client.create(ApiRequest::class.java)
}