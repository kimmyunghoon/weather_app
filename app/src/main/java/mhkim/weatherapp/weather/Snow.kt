package mhkim.weatherapp.weather

import android.content.res.Resources
import android.graphics.drawable.Drawable
import mhkim.weatherapp.common.Icon

data class Snow(override val main: String ,
                override var description: String= "none",
                override var id: Int= -1,
                override var temperature: Int= -1,
                override var pressure: Int= -1,
                override var humidity: Int= -1):WeatherInterface {
    override val icon
        get() = super.icon
    override fun getDrawable(resource: Resources): Drawable? {
        return super.getDrawable(resource)
    }

    override fun init(dataString: String) {
        TODO("Not yet implemented")
    }
}