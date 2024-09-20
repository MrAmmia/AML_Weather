package net.thebookofcode.www.amlweather.data.util

class Constants {
    companion object{
        const val BASE_URL = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/"
        const val TIMELINE = "next6days"
        const val ICONSET = "&iconSet=icons2"
        const val INCLUDES = "&include=days%2Chours%2fcst%2Ccurrent%2Chours%2Calerts"
        const val ELEMENTS = "&elements=tempmax,tempmin,temp,humidity,precip,preciptype,precibprob,winddir,windspeed,feelslike,icon,snow,snowdepth,datetime" +
                ",description,conditions,cloudcover"
        const val CITIES_INCLUDES = "&include=current"
        const val CITIES_ELEMENTS = "&elements=temp,humidity,precip,preciptype,precibprob,winddir,windspeed,icon,snow,snowdepth,datetime,conditions,cloudcover"
    }
}
