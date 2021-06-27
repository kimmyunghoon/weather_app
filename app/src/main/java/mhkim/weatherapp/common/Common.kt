package mhkim.weatherapp.common

import mhkim.weatherapp.R


enum class Icon(var main:String ,var description: String) {
    RAIN("Rain","Rain"),
    LIGHTRAIN("Rain","light rain"),
    MODRAIN("Rain","moderate rain"),
    HIRAIN("Rain","heavy intensity rain"),
    VHRAIN("Rain","very heavy rain"),
    EXTREMERAIN("Rain","extreme rain"),
    FREEZINGRAIN("Rain","freezing rain"),
    LISHWOERRAIN("Rain","light intensity shower rain"),
    HISHWOERRAIN("Rain","heavy intensity shower rain"),
    RAGGEDSHWOERRAIN("Rain","ragged shower rain"),
    SHOWERRAIN("Rain","shower rain"),


    CLEARSKY("Clear","clear sky"),

    THUNDERSTORM("Thunderstorm","thunderstorm"),


    SNOW("Snow","snow"),
    LIGHTSHOW("Snow","light snow"),
    HEAVYSNOW("Snow","Heavy snow"),
    SLEET("Snow","Sleet"),
    LIGHTSHOWERSLEET("Snow","Light shower sleet"),
    SHOWERSLEET("Snow","Shower sleet"),
    LIGHTRAINANDSNOW("Snow","Light rain and snow"),
    RAINANDSNOW("Snow","Rain and snow"),
    LIGHTSHOWERSNOW("Snow","Light shower snow"),
    SHOWERSNOW("Snow","Shower snow"),
    HEAVYSHOWERSHOW("Snow","Heavy shower snow"),

    MIST("Mist","mist"),
    SMOKE("Smoke","Smoke"),
    HAZE("Haze","Haze"),
    SANDDUST("Dust","sand/ dust whirls"),
    FOG("Fog","fog"),
    SAND("Sand","sand"),
    DUST("Dust","dust"),
    VOLCANICASH("Ash","volcanic ash"),
    SQUALLS("Squall","squalls"),
    TORNADO("Tornado","tornado"),

    FEWCLOUDS("Clouds","few clouds"),
    SCATTEREDCLOUDS("Clouds","scattered clouds"),
    BROKENCLOUDS("Clouds","broken clouds"),
    OVERCASTCLOUDS("Clouds","overcast clouds"),

    DEFAULT("DEFAULT","none"),
    ;

    companion object {
        fun from(main:String,description: String): Icon? = values().find { it.description.toLowerCase() == description.toLowerCase() || it.main.toLowerCase() == main.toLowerCase()   }
    }


    fun getValue(): Int {
        return when (this) {
            RAIN,LIGHTRAIN,MODRAIN,HIRAIN,VHRAIN,EXTREMERAIN -> R.drawable.rain_d

            CLEARSKY -> R.drawable.clearsky_d

            FEWCLOUDS -> R.drawable.fewclouds_d

            SCATTEREDCLOUDS -> R.drawable.scatteredclouds_d

            BROKENCLOUDS, OVERCASTCLOUDS -> R.drawable.brokenclouds_d

            THUNDERSTORM -> R.drawable.thunderstorm_d

            SNOW, LIGHTSHOW,
            HEAVYSNOW,
            SLEET,
            LIGHTSHOWERSLEET,
            SHOWERSLEET,
            LIGHTRAINANDSNOW,
            RAINANDSNOW,
            LIGHTSHOWERSNOW,
            SHOWERSNOW,
            HEAVYSHOWERSHOW,
            FREEZINGRAIN-> R.drawable.snow_d

            SHOWERRAIN,LISHWOERRAIN,HISHWOERRAIN,RAGGEDSHWOERRAIN-> R.drawable.showerrain_d

            MIST,
            SMOKE,
            HAZE,
            SANDDUST,
            FOG,
            SAND,
            DUST,
            VOLCANICASH,
            SQUALLS,
            TORNADO,
            -> R.drawable.mist_d
            else -> R.drawable.mist_d
        }
    }
}