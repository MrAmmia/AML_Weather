package net.thebookofcode.www.amlweather.entity

import com.google.gson.annotations.SerializedName
import java.util.*

data class Weather(
    @SerializedName("datetime")
    val dateTime : Date,
    val resolvedAddress:String?,
    val icon:String,

    @SerializedName("tempmin")
    val tempMin:Float,

    @SerializedName("tempmax")
    val tempMax:Float,

    val temp:Float,
    val description:String,
    val condition:String,
    val humidity:Float,
    val snow:Float?,

    @SerializedName("snowdepth")
    val snowDepth:Float?,

    @SerializedName("preciptype")
    val precipType:String,

    @SerializedName("precibprob")
    val precibProb:Float,

    @SerializedName("windspeed")
    val windSpeed:Float,

    @SerializedName("winddir")
    val windDir:Float
)