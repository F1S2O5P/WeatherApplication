package com.example.weatherapp

import com.example.weatherapp.model.forecast.WeatherForecastResponse
import com.example.weatherapp.model.weather.WeatherResponse
import com.example.weatherapp.network.RestWeatherService

class WeatherDataProvider(private val restService: RestWeatherService) {

    suspend fun getWeather(lat: String, lon: String): WeatherResponse {
        return restService.getCurrentWeatherData(lat, lon, Config.weatherAppId)
    }

    suspend fun getForecastWeather(lat: String, lon: String, days: Int): WeatherForecastResponse {
        return restService.getForecastWeatherData(lat, lon, days.toString(), Config.weatherForecastAppId)
    }

}