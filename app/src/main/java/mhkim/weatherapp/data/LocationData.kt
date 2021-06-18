package mhkim.weatherapp.data

import java.lang.StringBuilder

data class LocationData(val lat:Double, val lon:Double, var address: String ="데이터 없음"){
    override
    fun toString(): String {
        val msg = StringBuilder()
        msg.append("latitude : ").append(lat).append("\n")
            .append("longitude : ").append(lon).append("\n")
                .append("address : ").append(address).append("\n")
        return msg.toString()
    }
}
