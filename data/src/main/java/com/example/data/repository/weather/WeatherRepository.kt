package com.example.data.repository.weather

import com.example.data.repository.weather.entity.WeatherInfoEntity

interface WeatherRepository {

    suspend fun getWeatherList(): WeatherResult<List<WeatherInfoEntity>>
    suspend fun getWeatherDetails(weatherInfoId: String): WeatherResult<WeatherInfoEntity>
}