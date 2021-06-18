package mhkim.weatherapp.data

import java.lang.StringBuilder

data class WeatherData(var weather_type: String= "None", var temperature: Int= 0,var pressure: Int= -1, var humidity: Int= -1,var isRain:Boolean=false,var rain:Int= -1){

    fun getTemperature() : String {
        return "$temperature℃"
    }

    override
    fun toString(): String {
        val msg = StringBuilder()
        msg.append("weather_type : ").append(weather_type).append("\n")
            .append("temperature : ").append(temperature).append("\n")
            .append("pressure : ").append(pressure).append("\n")
            .append("humidity : ").append(humidity).append("\n")
            .append("isRain : ").append(isRain).append("\n")
            .append("rain : ").append(rain).append("\n")
        return msg.toString()
    }
}