package com.example.weatherapp.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.model.CurrentWeather
import kotlinx.android.synthetic.main.day_item.view.*

class WeatherAdapter : RecyclerView.Adapter<WeatherAdapter.WeatherHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherHolder {
        val inflatedView = LayoutInflater.from(parent.context).inflate(R.layout.day_item, parent, false)
        return WeatherHolder(inflatedView)
    }

    private val days = mutableListOf<CurrentWeather>()

    fun addList(listToAdd: List<CurrentWeather>) {
        if (days.isNotEmpty())
            days.clear()
        days.addAll(listToAdd)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return days.size
    }

    override fun onBindViewHolder(holder: WeatherHolder, position: Int) {
        holder.bind(days[position])
    }

    class WeatherHolder(val view: View) : RecyclerView.ViewHolder(view) {

        fun bind(currentWeather: CurrentWeather) {
            view.cityName.text = currentWeather.name
            view.temperature.text = currentWeather.temperature
            view.pressure.text = currentWeather.pressure
            view.humidity.text = currentWeather.humidity
            view.cloudiness.text = currentWeather.cloudiness
        }
    }
}