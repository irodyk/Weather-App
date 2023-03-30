package com.example.weatherapptest.weather.list

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.*
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapptest.databinding.FragmentWeatherListBinding
import com.example.weatherapptest.util.app
import com.example.weatherapptest.util.viewModel
import com.example.weatherapptest.weather._base.BaseFragment
import com.example.weatherapptest.weather.detail.model.WeatherDetails
import com.example.weatherapptest.weather.list.model.Weather
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import repositories

class WeatherListFragment : BaseFragment() {

    override val binding by lazy { FragmentWeatherListBinding.inflate(layoutInflater) }

    private val weatherListViewModel by lazy {
        viewModel(app) {
            WeatherListViewModel (
                app.repositories.weatherRepository,
                app.dispatcherProvider
            )
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.weatherListRecycler.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = WeatherListAdapter(::navigateToWeatherDetails)

        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                weatherListViewModel.weatherListStateFlow.collectLatest {
                    when (it) {
                        is WeatherListUiState.Success -> setWeatherList(it.weatherList)
                        is WeatherListUiState.Error -> Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                        is WeatherListUiState.Loading -> { /* display spinner */ }
                        is WeatherListUiState.Initial -> { /* nothing here */ }
                    }
                }
            }
        }

        weatherListViewModel.getWeatherList()
    }

    private fun setWeatherList(weatherList: List<Weather>) {
        val weatherListAdapter = binding.weatherListRecycler.adapter as? WeatherListAdapter
        weatherListAdapter?.updateWeatherList(weatherList)
    }

    private fun navigateToWeatherDetails(id: String) {
        val action = WeatherListFragmentDirections.actionWeatherDetails(id)
        findNavController().navigate(action)
    }
}