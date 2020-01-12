package com.example.weatherapp

import android.util.Log
import com.example.weatherapp.model.CurrentWeather
import com.example.weatherapp.network.RestWeatherService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class WeatherPresenter(private val view: WeatherView) {

    private val job = Job()
    private val ioContext = job + Dispatchers.IO
    private val ioScope = CoroutineScope(ioContext)

    private val mainContext = job + Dispatchers.Main
    private val mainScope = CoroutineScope(mainContext)

    private val dataProvider = WeatherDataProvider(RestServiceBuilder.build(RestWeatherService::class.java))

    fun fetchForecastData(lon: String = Config.userLongitude.value ?: "0.0", lat: String = Config.userLatitude.value ?: "0.0", daysCount: Int = 7) {

        ioScope.launch {
            val weatherDays = mutableListOf<CurrentWeather>()
            val weatherForecastData = dataProvider.getForecastWeather(lat, lon, daysCount)
            var daysToAdd = 0
            weatherForecastData.list.forEach {
                weatherDays.add(
                    CurrentWeather(
                        name = view.getDay(daysToAdd),
                        temperature = convertTemperature(it.temp.day),
                        humidity = it.humidity.toString(),
                        pressure = it.pressure.toString(),
                        cloudiness = it.clouds.toString() + "%"
                    )
                )
                daysToAdd++
                Config.willBeStormy = it.weather[0].description == "thunderstorm"
            }
            mainScope.launch {
                view.updateUI(weatherDays)
            }
        }
    }

    private fun convertTemperature(temp: Double): String {
        return "${String.format("%.1f", (temp - 273.15))} C"
    }


}