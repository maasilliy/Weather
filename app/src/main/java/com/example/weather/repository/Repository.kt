package com.example.weather.repository

import android.util.Log
import android.widget.Toast
import com.example.weather.api.RetrofitClient
import com.example.weather.model.Data
import java.lang.Exception

object Repository {


    suspend fun getWeather(city: String): Data{
        return RetrofitClient.apiRequest.getWeather(city)
    }
}