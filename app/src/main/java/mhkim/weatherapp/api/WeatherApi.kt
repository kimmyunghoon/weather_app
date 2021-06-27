package mhkim.weatherapp.api

import androidx.core.content.res.ResourcesCompat
import mhkim.weatherapp.BuildConfig
import mhkim.weatherapp.MainActivity
import mhkim.weatherapp.common.Icon
import mhkim.weatherapp.data.LocationData
import mhkim.weatherapp.data.WeatherData
import mhkim.weatherapp.databinding.ActivityWeatherBinding
import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException


/**
 * 날씨정보를 호출하는 API 구성 싱글톤으로 구성
 */
object WeatherApi {


    fun getCurrentWeather(binding: ActivityWeatherBinding, location: LocationData) {
        val url =
            "https://api.openweathermap.org/data/2.5/onecall?lat=${location.lat}&lon=${location.lon}&exclude=daily,minutely,hourly,alerts&appid=${BuildConfig.WEATHER_API_KEY}"
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
                val weather = JSONObject(dataStr)

                val current = JSONObject(weather.get("current").toString())
                data.humidity = current.get("humidity") as Int
                data.isRain = false
                data.pressure = current.get("pressure") as Int
                data.temperature = (current.get("temp") as Double - 273.15).toInt()
                val current_weather = JSONArray(current.get("weather").toString())
                val weather_data = JSONObject(current_weather[0].toString())
                data.weather_type = weather_data.get("main") as String
                data.weather_code = weather_data.get("id") as Int
                data.description = weather_data.get("description") as String

                if (response.isSuccessful) {
                    binding.activity?.let {
                        it.runOnUiThread {
                            //TODO: update your UI
                            val res = Icon.from(data.weather_type, data.description)
                             binding.weatherIcon.setImageDrawable(res?.getValue()?.let { it_res -> ResourcesCompat.getDrawable(it.resources, it_res, null) })
                        }
                    }
                    binding.location = location
                    binding.weather = data


                }

            }

            fun onSuccess(response: Response?) {
            }
        })


    }

    fun get7DaysWeather(binding: ActivityWeatherBinding, location: LocationData) {

        val url =
                "https://api.openweathermap.org/data/2.5/onecall?lat=${location.lat}&lon=${location.lon}&exclude=current,minutely,hourly,alerts&appid=${BuildConfig.WEATHER_API_KEY}"
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
                println("response get7DaysWeather : " + dataStr)
                val weather = JSONObject(dataStr)
                println("response weather : " + weather)
                val daily = JSONArray(weather.get("daily").toString())
                println("response daily : " + daily[0])
                for (day in daily) {
                    println("response day : " + day)
                }
//                data.humidity = current.get("humidity") as Int
//                data.isRain = false
//                data.pressure = current.get("pressure") as Int
//                data.temperature= (current.get("temp") as Double - 273.15).toInt()
//                println("response current: " + current)
//                val current_weather = JSONArray(current.get("weather").toString())
//                val weather_data = JSONObject(current_weather[0].toString())
//                data.weather_type = weather_data.get("main") as String
//                data.weather_code = weather_data.get("id") as Int
//                data.description = weather_data.get("description") as String
//
//                binding.weather = data


//                binding.location = location
            }
        })
    }
}

private operator fun JSONArray.iterator(): Iterator<Any?> = (0 until length()).asSequence().map { get(it) as JSONObject }.iterator()
