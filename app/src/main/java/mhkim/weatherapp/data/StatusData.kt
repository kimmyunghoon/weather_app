package mhkim.weatherapp.data

import java.lang.StringBuilder

data class StatusData(var location_confirm : Boolean = false) {

    override
    fun toString(): String {
        val msg = StringBuilder()
        msg.append("location_confirm : ").append(location_confirm).append("\n")
        return msg.toString()
    }
}