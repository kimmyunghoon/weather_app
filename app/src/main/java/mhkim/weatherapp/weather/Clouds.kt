package mhkim.weatherapp.weather

import android.content.res.Resources
import android.graphics.drawable.Drawable
import mhkim.weatherapp.common.Icon

data class Clouds(override var main: String = "none", override var description: String= "none", override var id: Int= -1, override val icon: Icon?=null):WeatherInterface {
    override fun getDrawable(resource: Resources): Drawable? {
        return super.getDrawable(resource)
    }

    override fun init(dataString: String) {
        TODO("Not yet implemented")
    }
}