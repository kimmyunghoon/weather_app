package mhkim.weatherapp

import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import mhkim.weatherapp.api.WeatherApi
import mhkim.weatherapp.data.LocationData
import mhkim.weatherapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null
    private var locationManager : LocationManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        locationManager = getSystemService(LOCATION_SERVICE) as LocationManager?


        try {
            // Request location updates
            locationManager?.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000L, 0f, locationListener)
            locationManager?.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000L, 0f, locationListener)
        } catch(ex: SecurityException) {
            Log.d("myTag", "Security Exception, no location available")
        }
    }
    //define the listener
    private val locationListener: LocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {

            Log.d("location", "${ location.latitude } / ${ location.longitude }")
            val locationData = LocationData(lat=location.latitude,lon=location.longitude)
            binding!!.location = locationData
            WeatherApi.getCurrentWeather(binding!!, LocationData(lat=location.latitude,lon=location.longitude) )
        }
        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
        override fun onProviderEnabled(provider: String) {}
        override fun onProviderDisabled(provider: String) {}
    }
}