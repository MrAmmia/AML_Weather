package net.thebookofcode.www.amlweather.data.remote.responses.entities

import com.google.gson.annotations.SerializedName

data class Days(
    @SerializedName("datetime")
    val datetime: String,

    @SerializedName("tempmax")
    val tempmax: Double,

    @SerializedName("tempmin")
    val tempmin: Double,

    @SerializedName("temp")
    val temp: Double,

    @SerializedName("feelslike")
    val feelslike: Double,

    @SerializedName("humidity")
    val humidity: Double,

    @SerializedName("precip")
    val precip: Double,

    @SerializedName("preciptype")
    val preciptype: List<String>? = null,

    @SerializedName("snow")
    val snow: Double,

    @SerializedName("snowdepth")
    val snowdepth: Double,

    @SerializedName("windspeed")
    val windspeed: Double,

    @SerializedName("winddir")
    val winddir: Double,

    @SerializedName("icon")
    val icon: String,

    @SerializedName("cloudcover")
    val cloudcover: Double,

    @SerializedName("hours")
    val hours: List<Hours>? = null,
)