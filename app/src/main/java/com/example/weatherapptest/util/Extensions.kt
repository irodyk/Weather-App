package com.example.weatherapptest.util

import androidx.fragment.app.Fragment
import com.example.weatherapptest.WeatherTestApplication

val Fragment.app
    get() = requireContext().applicationContext as WeatherTestApplication