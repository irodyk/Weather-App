package com.example.data.repository.weather.response

import com.example.data.util.EMPTY

data class Weather (
    val id: String = String.EMPTY,
    val name: String = String.EMPTY,
    val main: Main = Main(),
    val wind: Wind = Wind()
)

data class Main (
    val temp_min: Float = 0f,
    val temp_max: Float = 0f,
    val pressure: Float = 0f
)

data class Wind (
    val speed: Float = 0f
)