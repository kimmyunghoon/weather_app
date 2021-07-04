package mhkim.weatherapp.api

import androidx.core.content.res.ResourcesCompat
import mhkim.weatherapp.BuildConfig
import mhkim.weatherapp.MainActivity
import mhkim.weatherapp.common.DataParser
import mhkim.weatherapp.common.Icon
import mhkim.weatherapp.common.WeatherBuilder
import mhkim.weatherapp.data.LocationData
import mhkim.weatherapp.data.WeatherData
import mhkim.weatherapp.databinding.ActivityWeatherBinding
import mhkim.weatherapp.weather.Clear
import mhkim.weatherapp.weather.WeatherInterface
import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException


/**
 * 날씨정보를 호출하는 API 구성 싱글톤으로 구성
 * 요청 횟수 관련 된 이슈로 백엔드 구성 후 진행 : Todo
 */
object WeatherApi {


    fun getCurrentWeather(binding: ActivityWeatherBinding, location: LocationData) {
        val url =
            "https://api.openweathermap.org/data/2.5/onecall?lat=${location.lat}&lon=${location.lon}&exclude=daily,minutely,hourly,alerts&appid=${BuildConfig.WEATHER_API_KEY}"
        val client = OkHttpClient()
        val request = Request.Builder().url(url).build()
        val data = WeatherData()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                //에러 메세지 출력
            }

            override fun onResponse(call: Call, response: Response) {


                val dataStr: String = response.body()?.string()!!


                val weather =  DataParser.CURRENT.toObject(dataStr)

                val current = JSONObject(weather.get("current").toString())
                data.humidity = current.get("humidity") as Int

                data.pressure = current.get("pressure") as Int
                data.temperature = (current.get("temp") as Double - 273.15).toInt()
                val current_weather = JSONArray(current.get("weather").toString())
                val weather_data = JSONObject(current_weather[0].toString())

                val weatherValue = WeatherBuilder.from(weather_data.get("main") as String)

                data.weather_type = weather_data.get("main") as String
                data.isRain =  data.weather_type === "rain"
                data.weather_code = weather_data.get("id") as Int
                data.description = weather_data.get("description") as String

                if (response.isSuccessful) {
                    binding.activity?.let {
                        it.runOnUiThread {
                            binding.weatherIcon.setImageDrawable(weatherValue?.getDrawable(it.resources))
                            binding.location = location
                            binding.weather = data
                        }
                    }



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
                    /**
                     *  {"dt":1625367600,
                     *  "sunrise":1625343450,
                     *  "sunset":1625395899,
                     *  "moonrise":1625329200,
                     *  "moonset":1625377380,
                     *  "moon_phase":0.82,
                     *  "temp":{"day":300.37,"min":291.59,"max":304.76,"night":296.99,"eve":301.04,"morn":291.59},
                     *  "feels_like":{"day":301.28,"night":297.44,"eve":303,"morn":291.97},
                     *  "pressure":1011,
                     *  "humidity":57,
                     *  "dew_point":290.63,
                     *  "wind_speed":4.15,
                     *  "wind_deg":96,
                     *  "wind_gust":7.28,
                     *  "weather":[{"id":804,"main":"Clouds","description":"overcast clouds","icon":"04d"}],"clouds":98,"pop":0.03,"uvi":10}
                     */
                }
            }
        })
    }
}

private operator fun JSONArray.iterator(): Iterator<Any?> = (0 until length()).asSequence().map { get(it) as JSONObject }.iterator()
