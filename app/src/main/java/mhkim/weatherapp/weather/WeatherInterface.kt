package mhkim.weatherapp.weather

import android.content.res.Resources
import android.graphics.drawable.Drawable
import androidx.core.content.res.ResourcesCompat
import mhkim.weatherapp.common.Icon

interface WeatherInterface {
    val main : String
    var description : String
    var id : Int
    val icon : Icon?
        get() =  Icon.from(main , description)

    var temperature: Int
    var pressure: Int
    var humidity: Int
    fun getDrawable(resource: Resources): Drawable? = icon?.getValue()?.let { ResourcesCompat.getDrawable(resource , it, null) }

    fun init(dataString : String)

}