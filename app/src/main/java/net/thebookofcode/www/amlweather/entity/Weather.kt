package net.thebookofcode.www.amlweather.entity

import com.google.gson.annotations.SerializedName
import org.json.JSONArray
import java.util.*
import kotlin.collections.ArrayList

/*

To get weather within a specified time range
 https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/London,UK/2020-10-01/2020-12-31?key=YOUR_API_KEY

To get weather for a specific time
https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/London,UK/2020-12-15T13:00:00?key=YOUR_API_KEY

Using elements list and options parameter to request only daily with limited elements
https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/London,UK/next6days?key=V298VZTM3M5G26YJLZW495QF9&include=days%2Chours%2Ccurrent&elements=tempmax,tempmin,temp,humidity

days – daily data
hours – hourly data
alerts – weather alerts
current – current conditions or conditions at requested time.
events – historical events such as a hail, tornadoes, wind damage and earthquakes (not enabled by default)
obs – historical observations from weather stations
remote – historical observations from remote source such as satellite or radar
fcst – forecast based on 16 day models.
stats – historical statistical normals and daily statistical forecast
statsfcst – use the full statistical forecast information for dates in the future beyond the current model forecast. Permits hourly statistical forecast.


final query sample
https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/London,UK/next6days?key=V298VZTM3M5G26YJLZW495QF9&include=days%2Chours%2fcst%2Ccurrent%2Chours&elements=tempmax,tempmin,temp,humidity,precip,preciptype,precibprob,winddir,windspeed,feelslike,icon,snow,snowdepth,datetime
include days,hours
 */

data class Weather(
    @SerializedName("latitude")
    val latitude: Double,

    @SerializedName("longitude")
    val longitude: Double,

    @SerializedName("resolvedAddress")
    val resolvedAddress: String,

    @SerializedName("address")
    val address: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("conditions")
    val condition: String,


    @SerializedName("timezone")
    val timezone: String,

    @SerializedName("tzoffset")
    val tzoffset: Double,

    @SerializedName("days")
    val days: List<Days>,

    @SerializedName("currentConditions")
    val currentConditions: CurrentConditions
)