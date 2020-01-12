package com.example.weatherapp.model

data class CurrentWeather(
    val name: String,
    val temperature: String,
    val pressure: String,
    val humidity: String,
    val cloudiness: String
)