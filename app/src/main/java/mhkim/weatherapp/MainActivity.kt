package mhkim.weatherapp

import android.location.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TableLayout
import androidx.databinding.DataBindingUtil
import com.google.android.material.tabs.TabLayout
import mhkim.weatherapp.api.WeatherApi
import mhkim.weatherapp.data.LocationData
import mhkim.weatherapp.databinding.ActivityWeatherBinding
import java.io.IOException
import java.util.*

class MainActivity : AppCompatActivity() {
    private var binding: ActivityWeatherBinding? = null
    private var locationManager : LocationManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /**
         * Todo : 날씨에 따른 화면 바인딩 처리 추가 및 데이터 연동 추가
         */
        binding = DataBindingUtil.setContentView(this,R.layout.activity_weather)
        val today  = Calendar.getInstance()
        Log.d("TAG", today.get(Calendar.DAY_OF_WEEK).toString())
        binding?.daysTab?.getTabAt(  today.get(Calendar.DAY_OF_WEEK)-1)?.select();
        binding?.daysTab?.touchables?.forEach { it.isEnabled = false }


        /**
         * Todo : 위치관련 권한 요청 기능 추가 - 앱 로딩후 바로 출력 -> 승인시 앱 기능 바로 이용 미승인시 상단에 알림창을 통해서 승인 요청 알림 표시
         */
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
            val geocoder = Geocoder(applicationContext)
            val addresses: List<Address>?
            val address: Address?
            val locationData = LocationData(lat=location.latitude,lon=location.longitude)
            try {
                addresses = geocoder.getFromLocation(locationData.lat, locationData.lon, 1)
                if (null != addresses && !addresses.isEmpty()) {
                    address = addresses[0]

                    locationData.address  = "${address.adminArea} ${address.thoroughfare}"
                }
            } catch (e: IOException) {
                Log.e("MapsActivity", e.localizedMessage)
            }

            WeatherApi.getCurrentWeather(binding!!, locationData)
        }
        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
        override fun onProviderEnabled(provider: String) {}
        override fun onProviderDisabled(provider: String) {}
    }
}