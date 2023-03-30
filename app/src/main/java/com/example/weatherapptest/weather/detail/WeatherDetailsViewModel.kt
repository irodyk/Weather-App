package com.example.weatherapptest.weather.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.repository.weather.WeatherRepository
import com.example.data.util.EMPTY
import com.example.weatherapptest.util.DispatcherProvider
import com.example.weatherapptest.weather.detail.mapper.mapEntityToUiModel
import com.example.weatherapptest.weather.detail.model.WeatherDetails
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class WeatherDetailsUiState {
    data class Success(val weatherDetails: WeatherDetails) : WeatherDetailsUiState()
    data class Error(val message: String) : WeatherDetailsUiState()
    object Loading : WeatherDetailsUiState()
    object Initial : WeatherDetailsUiState()
}

class WeatherDetailsViewModel (
    private val weatherRepository: WeatherRepository,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {

    private val _weatherDetailsStateFlow = MutableStateFlow<WeatherDetailsUiState> (
        WeatherDetailsUiState.Initial
    )
    val weatherDetailsStateFlow = _weatherDetailsStateFlow.asStateFlow()

    fun getWeatherDetails(weatherId: String) {
        viewModelScope.launch(dispatcherProvider.io) {
            val result = weatherRepository.getWeatherDetails(weatherId)
            when {
                result.isSuccess -> {
                    _weatherDetailsStateFlow.value = WeatherDetailsUiState.Success (
                        result.response.mapEntityToUiModel()
                    )
                }
                else -> {
                    _weatherDetailsStateFlow.value = WeatherDetailsUiState.Error (
                        result.exception?.message ?: String.EMPTY
                    )
                }
            }
        }
    }
}
