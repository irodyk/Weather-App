package com.example.weatherapptest.weather.list.mapper

import com.example.data.repository.weather.entity.WeatherInfoEntity
import com.example.weatherapptest.weather.list.model.Weather
import kotlin.math.roundToInt

fun List<WeatherInfoEntity>.mapWeatherEntityListToWeatherList() = map { weather ->
    with(weather) {
        Weather (
            id,
            name,
            tempMin.roundToInt(),
            tempMax.roundToInt()
        )
    }
}.toList()