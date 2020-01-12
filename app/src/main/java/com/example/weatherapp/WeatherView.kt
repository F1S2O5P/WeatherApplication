package com.example.weatherapp

import com.example.weatherapp.model.CurrentWeather

interface WeatherView {
    fun updateUI(weather: List<CurrentWeather>)
    fun getDay(addDays: Int):String
}