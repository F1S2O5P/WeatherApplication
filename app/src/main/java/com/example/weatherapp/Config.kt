package com.example.weatherapp

import androidx.lifecycle.MutableLiveData

object Config {
    val weatherAppId = "removedDueToGitHubPublicRepo"
    val weatherForecastAppId = "removedDueToGitHubPublicRepo"

    var userLongitude = MutableLiveData<String>().apply { value = "0.0" }
    var userLatitude = MutableLiveData<String>().apply { value = "0.0" }

    var willBeStormy: Boolean = false
}