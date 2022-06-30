package net.thebookofcode.www.amlweather.entity

import com.google.gson.annotations.SerializedName

data class CurrentConditions(
    @SerializedName("datetime")
    val datetime: String,

    @SerializedName("temp")
    val temp: Double,

    @SerializedName("feelslike")
    val feelslike: Double,

    @SerializedName("humidity")
    val humidity: Double,

    @SerializedName("precip")
    val precip: Double,

    @SerializedName("snow")
    val snow: Double,

    @SerializedName("snowdepth")
    val snowdepth: Double,

    @SerializedName("conditions")
    val condition: String,

    @SerializedName("preciptype")
    val preciptype: Object,

    @SerializedName("windspeed")
    val windspeed: Double,

    @SerializedName("winddir")
    val winddir: Double,

    @SerializedName("cloudcover")
    val cloudcover: Double,

    @SerializedName("icon")
    val icon: String

)
