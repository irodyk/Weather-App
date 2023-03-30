package com.example.weatherapptest.weather.list

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapptest.databinding.ItemWeatherBinding
import com.example.weatherapptest.weather.list.model.Weather

class WeatherListAdapter (
    private val itemCLickAction: (String) -> Unit
) : RecyclerView.Adapter<WeatherListAdapter.WeatherListViewHolder>() {

    private val weatherList = mutableListOf<Weather>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherListViewHolder =
        WeatherListViewHolder (
            ItemWeatherBinding.inflate(LayoutInflater.from(parent.context))
        )

    override fun getItemCount() = weatherList.size

    override fun onBindViewHolder(holder: WeatherListViewHolder, position: Int) {
        holder.bindWeatherItem(weatherList[position])
        holder.itemView.setOnClickListener {
            itemCLickAction (
                weatherList[position].id
            )
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateWeatherList(newWeatherList: List<Weather>) {
        weatherList.clear()
        weatherList.addAll(newWeatherList)
        notifyDataSetChanged()
    }

    inner class WeatherListViewHolder (
        private val binding: ItemWeatherBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bindWeatherItem(weather: Weather) {
            binding.name.text = weather.name
            val minMaxTemp = "${weather.minTemp} - ${weather.maxTemp} C"
            binding.minMaxTemp.text = minMaxTemp
        }
    }
}