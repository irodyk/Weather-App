package com.example.weatherapptest.weather.detail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.*
import androidx.navigation.fragment.navArgs
import com.example.weatherapptest.R
import com.example.weatherapptest.databinding.FragmentWeatherDetailsBinding
import com.example.weatherapptest.util.*
import com.example.weatherapptest.weather._base.BaseFragment
import com.example.weatherapptest.weather.detail.model.WeatherDetails
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import repositories

class WeatherDetailsFragment : BaseFragment() {

    override val binding by lazy { FragmentWeatherDetailsBinding.inflate(layoutInflater) }

    private val weatherDetailsViewModel by lazy {
        viewModel(app) {
            WeatherDetailsViewModel (
                app.repositories.weatherRepository,
                app.dispatcherProvider
            )
        }
    }

    private val args: WeatherDetailsFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                weatherDetailsViewModel.weatherDetailsStateFlow.collectLatest {
                    when (it) {
                        is WeatherDetailsUiState.Success -> setWeatherDetails(it.weatherDetails)
                        is WeatherDetailsUiState.Error -> Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                        is WeatherDetailsUiState.Loading -> { /* display spinner */ }
                        is WeatherDetailsUiState.Initial -> { /* nothing here */ }
                    }
                }
            }
        }

        weatherDetailsViewModel.getWeatherDetails(args.weatherId)
    }

    private fun setWeatherDetails(weatherDetails: WeatherDetails) {
        with(weatherDetails) {
           binding.name.text = name
            binding.minMaxTemp.text = getString (R.string.temp_min_max, minTemp, maxTemp)
            binding.windSpeed.text = getString(R.string.wind_speed, windSpeed)
            binding.pressure.text = getString(R.string.pressure, pressure)
        }
    }
}