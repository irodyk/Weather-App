package com.example.weatherapptest

import RepositoryApplication
import com.example.weatherapptest.util.DefaultDispatcherProvider
import com.example.weatherapptest.util.DispatcherProvider

interface WeatherApp {
    val dispatcherProvider: DispatcherProvider
}

class WeatherTestApplication : RepositoryApplication(), WeatherApp {

    override val dispatcherProvider = DefaultDispatcherProvider()
}
