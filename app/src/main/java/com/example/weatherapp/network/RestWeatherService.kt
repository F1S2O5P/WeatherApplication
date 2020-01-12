package com.example.weatherapp.network

import com.example.weatherapp.model.forecast.WeatherForecastResponse
import com.example.weatherapp.model.weather.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface RestWeatherService {

    @GET("data/2.5/weather?")
    suspend fun getCurrentWeatherData(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("APPID") app_id: String): WeatherResponse

    @GET("data/2.5/forecast/daily?")
    suspend fun getForecastWeatherData(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("cnt") cnt: String,
        @Query("APPID") app_id: String): WeatherForecastResponse

}