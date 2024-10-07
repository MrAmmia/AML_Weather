package net.thebookofcode.www.amlweather.data.util

class Constants {
    companion object {
        const val BASE_URL =
            "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/"
        const val TIMELINE = "next6days"
        const val ICONSET = "&iconSet=icons2"
        const val INCLUDES = "&include=days%2Chours%2fcst%2Ccurrent%2Chours%2Calerts"
        const val ELEMENTS =
            "&elements=tempMax,tempMin,temp,humidity,precip,precipType,precibprob,windDir,windSpeed,feelsLike,icon,snow,snowDepth,datetime" +
                    ",description,conditions,cloudCover"
        const val CITIES_INCLUDES = "&include=current"
        const val CITIES_ELEMENTS =
            "&elements=temp,humidity,precip,precipType,precibprob,windDir,windSpeed,icon,snow,snowDepth,datetime,conditions,cloudCover"

        const val STALE_THRESHOLD = 60 * 60 * 1000
    }
}
