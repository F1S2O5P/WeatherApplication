package com.example.weatherapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import kotlinx.android.synthetic.main.activity_weather.*
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.util.Log
import com.mapbox.geojson.Point
import com.mapbox.mapboxsdk.annotations.MarkerOptions
import com.mapbox.mapboxsdk.geometry.LatLng


class WeatherActivity : AppCompatActivity(), OnMapReadyCallback, MapboxMap.OnMapClickListener {

    private lateinit var map: MapboxMap

    override fun onMapReady(mapboxMap: MapboxMap) {
        mapboxMap.setStyle(Style.Builder().fromUri("mapbox://styles/mapbox/cjf4m44iw0uza2spb3q0a7s41"))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Mapbox.getInstance(applicationContext, getString(R.string.access_token))
        setContentView(R.layout.activity_weather)
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this)
        mapView.getMapAsync {
            map = it
            map.addOnMapClickListener(this)
        }
        weatherButton.setOnClickListener {
            finish()
        }

    }

    override fun onMapClick(point: LatLng): Boolean {
        if (!map.markers.isEmpty()) {
            map.clear()
        }

        val marker = map.addMarker(MarkerOptions().position(point))
        val position = Point.fromLngLat(point.longitude, point.latitude)

        Config.userLatitude.value = position.latitude().toString()
        Config.userLongitude.value = position.longitude().toString()

        weatherButton.isEnabled = true

        return true
    }

    public override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
    }

    public override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }
}
