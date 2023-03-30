package com.example.weatherapptest.weather.detail.model

data class WeatherDetails (
    val id: String,
    val name: String,
    val minTemp: Int,
    val maxTemp: Int,
    val pressure: Int,
    val windSpeed: Int
)