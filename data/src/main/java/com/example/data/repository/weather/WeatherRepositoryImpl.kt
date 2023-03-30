package com.example.data.repository.weather

import com.example.data.repository.weather.entity.WeatherInfoEntity
import com.example.data.repository.weather.mapper.mapWeatherDetailsResponseToEntity
import com.example.data.repository.weather.mapper.mapWeatherListResponseToEntityList

data class WeatherResult<T>(
    val response: T,
    val exception: Exception? = null,
    val isSuccess: Boolean = exception == null
)

class WeatherRepositoryImpl (
    private val weatherApi: WeatherApi
) : WeatherRepository {

    override suspend fun getWeatherList(): WeatherResult<List<WeatherInfoEntity>> {
        return try {
            val response = weatherApi.getWeatherList().mapWeatherListResponseToEntityList()
            WeatherResult(response)
        } catch (e: Exception) {
            WeatherResult(emptyList(), e)
        }
    }

    override suspend fun getWeatherDetails(weatherInfoId: String): WeatherResult<WeatherInfoEntity> {
        return try {
            val response = weatherApi.getWeatherDetails(weatherInfoId).mapWeatherDetailsResponseToEntity()
            WeatherResult(response)
        } catch (e: Exception) {
            WeatherResult(WeatherInfoEntity(), e)
        }
    }
}