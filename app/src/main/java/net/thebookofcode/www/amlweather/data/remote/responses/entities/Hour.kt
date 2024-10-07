package net.thebookofcode.www.amlweather.data.remote.responses.entities

import com.google.gson.annotations.SerializedName

data class Hour(
    @SerializedName("datetime")
    val datetime: String,

    @SerializedName("temp")
    val temp: Double,

    @SerializedName("feelsLike")
    val feelsLike: Double,

    @SerializedName("humidity")
    val humidity: Double,

    @SerializedName("precip")
    val precip: Double,

    @SerializedName("precipprop")
    val precipProp: Double,

    @SerializedName("snow")
    val snow: Double,

    @SerializedName("snowDepth")
    val snowDepth: Double,

    /*@SerializedName("precipType")
    val precipType: String,*/
    
    @SerializedName("windSpeed")
    val windSpeed: Double,

    @SerializedName("windDir")
    val windDir: Double,

    @SerializedName("icon")
    val icon: String,
)