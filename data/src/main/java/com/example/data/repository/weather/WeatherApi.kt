package com.example.data.repository.weather

import com.example.data.repository.weather.response.Weather
import com.example.data.repository.weather.response.WeatherListResponse
import retrofit2.http.*

interface WeatherApi {

    @GET("olestang/weather/list")
    suspend fun getWeatherList() : WeatherListResponse

    @GET("olestang/weather/{id}")
    suspend fun getWeatherDetails(@Path("id") id: String) : Weather
}