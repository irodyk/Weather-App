package com.example.weatherapptest.util

import androidx.lifecycle.*
import com.example.weatherapptest.WeatherTestApplication

inline fun <reified T : ViewModel> ViewModelStoreOwner.viewModel(
    weatherApp: WeatherTestApplication,
    crossinline factory: (weatherApp: WeatherTestApplication) -> T
): T = T::class.java.let { clazz ->
        ViewModelProvider (
            this,
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    if (modelClass == clazz) {
                        @Suppress("UNCHECKED_CAST")
                        return factory(weatherApp) as T
                    }
                    throw IllegalArgumentException("Unexpected argument: $modelClass")
                }
            }
        ).get(clazz)
    }