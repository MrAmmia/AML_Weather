package net.thebookofcode.www.amlweather.util

class Constants {
    companion object{
        const val BASE_URL = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/"
        const val API_KEY = "?key=V298VZTM3M5G26YJLZW495QF9"
        const val TIMELINE = "next6days"
        const val INCLUDES = "&include=days%2Chours%2fcst%2Ccurrent%2Chours"
        const val ELEMENTS = "&elements=tempmax,tempmin,temp,humidity,precip,preciptype,precibprob,winddir,windspeed,feelslike,icon,snow,snowdepth,datetime"
    }
}
