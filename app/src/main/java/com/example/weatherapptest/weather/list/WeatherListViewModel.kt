package com.example.weatherapptest.weather.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.repository.weather.WeatherRepository
import com.example.data.util.EMPTY
import com.example.weatherapptest.util.DispatcherProvider
import com.example.weatherapptest.weather.list.mapper.mapWeatherEntityListToWeatherList
import com.example.weatherapptest.weather.list.model.Weather
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class WeatherListUiState {
    data class Success(val weatherList: List<Weather>) : WeatherListUiState()
    data class Error(val message: String) : WeatherListUiState()
    object Loading : WeatherListUiState()
    object Initial : WeatherListUiState()
}

class WeatherListViewModel (
    private val weatherRepository: WeatherRepository,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {

    private val _weatherListStateFlow = MutableStateFlow<WeatherListUiState> (
        WeatherListUiState.Initial
    )
    val weatherListStateFlow = _weatherListStateFlow.asStateFlow()

    fun getWeatherList() {
        viewModelScope.launch(dispatcherProvider.io) {
            val result = weatherRepository.getWeatherList()
            when {
                result.isSuccess -> {
                    _weatherListStateFlow.value = WeatherListUiState.Success (
                        result.response.mapWeatherEntityListToWeatherList()
                    )
                }
                else -> {
                    _weatherListStateFlow.value = WeatherListUiState.Error (
                        result.exception?.message ?: String.EMPTY
                    )
                }
            }
        }
    }
}