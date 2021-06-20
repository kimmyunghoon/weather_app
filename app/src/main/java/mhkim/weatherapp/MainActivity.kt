package mhkim.weatherapp

import android.Manifest
import android.content.pm.PackageManager
import android.location.*
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TableLayout
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.google.android.material.tabs.TabLayout
import mhkim.weatherapp.api.WeatherApi
import mhkim.weatherapp.data.LocationData
import mhkim.weatherapp.data.StatusData
import mhkim.weatherapp.data.WeatherData
import mhkim.weatherapp.databinding.ActivityWeatherBinding
import java.io.IOException
import java.util.*

class MainActivity : AppCompatActivity() {
    private var binding: ActivityWeatherBinding? = null
    private var locationManager : LocationManager? = null
    private var permission_status : StatusData = StatusData()
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /**
         * Todo : 날씨에 따른 화면 바인딩 처리 추가 및 데이터 연동 추가
         */
        binding = DataBindingUtil.setContentView(this,R.layout.activity_weather)
        binding?.activity = this
        binding?.weather = WeatherData()
        binding?.location = LocationData(0.0,0.0,"None")

        val today  = Calendar.getInstance()
        binding?.daysTab?.getTabAt(  today.get(Calendar.DAY_OF_WEEK)-1)?.select();
        binding?.daysTab?.touchables?.forEach { it.isEnabled = false }


        Log.d("TAG", (ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED  ).toString())
       if(ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED  ){
           permission_status.location_confirm = true

        }
        if(shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)){
            /**
             * Todo : 왜 위치 권한이 필요한지에 대한 정보가 노출되어야함.
             */
            permission_status.location_confirm = false
        }

        Log.d("TAG", permission_status.toString())
        binding?.status = permission_status
    }


    val requestPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()
            ) { isGranted: Boolean ->
                Log.d("Location Comfirm","$isGranted")
                if (isGranted) {

                    permission_status.location_confirm = isGranted

                    binding?.status = permission_status
                    locationManager = getSystemService(LOCATION_SERVICE) as LocationManager?
                    try {
                        locationManager?.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000L, 100f, locationListener)
                        locationManager?.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10000L, 100f, locationListener)
                    } catch(ex: SecurityException) {
                        Log.d("myTag", "Security Exception, no location available")
                    }
                } else {
                    permission_status.location_confirm = isGranted

                    binding?.status = permission_status
                }
            }
    fun locationConfirm(){
        /**
         * Todo : 위치관련 권한 요청 기능 추가 - 앱 로딩후 바로 출력 -> 승인시 앱 기능 바로 이용 미승인시 상단에 알림창을 통해서 승인 요청 알림 표시
         */
        Log.d("locationConfirm","RUN")
        requestPermissionLauncher.launch(
                    Manifest.permission.ACCESS_FINE_LOCATION)
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