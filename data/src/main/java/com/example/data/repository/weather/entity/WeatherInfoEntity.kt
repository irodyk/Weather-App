package com.example.data.repository.weather.entity

import com.example.data.util.EMPTY

// for the sake of example we keep the structure flat with
data class WeatherInfoEntity (
    val id: String = String.EMPTY,
    val name: String = String.EMPTY,
    val tempMin: Float = 0f,
    val tempMax: Float = 0f,
    val windSpeed: Float = 0f,
    val pressure: Float = 0f
)