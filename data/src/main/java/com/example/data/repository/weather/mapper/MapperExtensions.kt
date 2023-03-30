package com.example.data.repository.weather.mapper

import com.example.data.repository.weather.entity.WeatherInfoEntity
import com.example.data.repository.weather.response.Weather
import com.example.data.repository.weather.response.WeatherListResponse

fun WeatherListResponse.mapWeatherListResponseToEntityList() = list.map {
    it.mapWeatherDetailsResponseToEntity()
}

fun Weather.mapWeatherDetailsResponseToEntity() = WeatherInfoEntity (
    id,
    name,
    main.temp_min,
    main.temp_max,
    wind.speed,
    main.pressure
)