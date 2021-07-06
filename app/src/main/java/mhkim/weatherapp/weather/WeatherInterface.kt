package mhkim.weatherapp.weather

import android.content.res.Resources
import android.graphics.drawable.Drawable
import androidx.core.content.res.ResourcesCompat
import mhkim.weatherapp.common.Icon

interface WeatherInterface {
    var main : String
    var description : String
    var id : Int
    val icon : Icon?
        get() =  Icon.from(main, description)

    fun getDrawable(resource: Resources): Drawable? {
        return icon?.getValue()?.let { ResourcesCompat.getDrawable(resource , it, null) }
    }

    fun init(dataString : String)

}