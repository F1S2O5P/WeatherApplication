package com.example.weatherapp

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapp.adapters.WeatherAdapter
import com.example.weatherapp.model.CurrentWeather
import kotlinx.android.synthetic.main.activity_main.*
import org.joda.time.DateTime
import java.util.*


class MainActivity : AppCompatActivity(), WeatherView {


    //location
    private var locationManager: LocationManager? = null

    private val presenter = WeatherPresenter(this)
    private val weatherAdapter = WeatherAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        weatherForecast.layoutManager = LinearLayoutManager(this)
        weatherForecast.adapter = weatherAdapter

        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)

        Config.userLatitude.observe(this, androidx.lifecycle.Observer {
            presenter.fetchForecastData()
        })

        Config.userLongitude.observe(this, androidx.lifecycle.Observer {
            presenter.fetchForecastData()
        })

        setupUserLocation()

        forecastButton.setOnClickListener {
            setupUserLocation()
        }

        mapButton.setOnClickListener {
            val intent = Intent(this, WeatherActivity::class.java)
            startActivityForResult(intent, 1)
        }

        stormButton.setOnClickListener {
            if(Config.willBeStormy){
                Toast.makeText(this, "There will be storm in next week", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "There won't be storm in next week", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun updateUI(weather: List<CurrentWeather>) {
        Log.i("supertest123","updated ui with ${weather.toString()}")
        weatherAdapter.addList(weather)
        lan.text = Config.userLatitude.value ?: "0.0"
        lon.text = Config.userLongitude.value ?: "0.0"
    }


    //GET USER LOCATION
    private fun setupUserLocation() {
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (!locationManager!!.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Toast.makeText(this, "Enable location!", Toast.LENGTH_LONG).show()
        } else if (locationManager!!.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            getLocation()
        }
    }

    private fun getLocation() {
        val fineLocation = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
        val coarseLocation = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
        if (fineLocation != PackageManager.PERMISSION_GRANTED && coarseLocation != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1) //if didn't get permission ask for it
        } else {
            val location = locationManager?.getLastKnownLocation(LocationManager.GPS_PROVIDER) //if there is permission get lat and lon

            if (location != null) {
                Config.userLatitude.value = location.latitude.toString()
                Config.userLongitude.value = location.longitude.toString()
                Log.i("supertest123",Config.userLongitude.value ?: "bbb")
                Log.i("supertest123",Config.userLatitude.value ?: "aaa")

            }
        }
    }

    override fun getDay(addDays: Int):String{

        var mojaData = Date()
        var dt = DateTime(mojaData)
        var dayOfWeek = dt.plusDays(addDays).dayOfWeek().get()

        when(dayOfWeek){
            1 -> return "poniedzialek"
            2 -> return "wtorek"
            3 -> return "sroda"
            4 -> return "czwartek"
            5 -> return "piatek"
            6 -> return "sobota"
            7 -> return "niedziela"
        }
        return "nie ma takiego dnia"
    }

}
