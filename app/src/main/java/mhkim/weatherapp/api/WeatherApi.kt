package mhkim.weatherapp.api

import mhkim.weatherapp.BuildConfig
import mhkim.weatherapp.data.LocationData
import mhkim.weatherapp.data.WeatherData
import mhkim.weatherapp.databinding.ActivityMainBinding
import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException


/**
 * 날씨정보를 호출하는 API 구성 싱글톤으로 구성
 */
object WeatherApi {


    fun getCurrentWeather(binding: ActivityMainBinding, location: LocationData) {
        val url =
            "https://api.openweathermap.org/data/2.5/onecall?lat=${location.lat}&lon=${location.lon}&appid=${BuildConfig.WEATHER_API_KEY}"
        val client = OkHttpClient()
        val request = Request.Builder().url(url).build()
        val data = WeatherData()
        println("url : " + url)
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                //에러 메세지 출력
            }

            override fun onResponse(call: Call, response: Response) {


                val dataStr: String = response.body()?.string()!!
                println("response : " + dataStr)
                val weather = JSONObject(dataStr)

                val current = JSONObject(weather.get("current").toString())
                data.humidity = current.get("humidity") as Int
                data.isRain = false
                data.pressure = current.get("pressure") as Int
                println("response current: " + current)
                val current_weather = JSONArray(current.get("weather").toString())
                data.weather_type = JSONObject(current_weather[0].toString()).get("main") as String

                binding.weather = data
            }
        })


    }

    fun get7DaysWeather() {

    }
}