package com.example.weatherapptest.weather.detail.mapper

import com.example.data.repository.weather.entity.WeatherInfoEntity
import com.example.weatherapptest.weather.detail.model.WeatherDetails
import kotlin.math.roundToInt

fun WeatherInfoEntity.mapEntityToUiModel() = WeatherDetails (
    id,
    name,
    tempMin.roundToInt(),
    tempMax.roundToInt(),
    pressure.roundToInt(),
    windSpeed.roundToInt() // we don't need that precision for display
)