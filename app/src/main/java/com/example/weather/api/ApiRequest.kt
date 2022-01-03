package com.example.weather.api

import com.example.weather.model.Data
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiRequest {

    @GET("data/2.5/weather?,ua&appid=c2ce4892af99024aea4e9ab245dc516b")
    suspend fun getWeather(@Query("q") city: String): Data
}