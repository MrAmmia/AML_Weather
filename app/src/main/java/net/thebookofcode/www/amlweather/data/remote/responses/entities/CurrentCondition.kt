package net.thebookofcode.www.amlweather.data.remote.responses.entities

import com.google.gson.annotations.SerializedName

data class CurrentCondition(
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

    @SerializedName("snow")
    val snow: Double,

    @SerializedName("snowDepth")
    val snowDepth: Double,

    @SerializedName("conditions")
    val condition: String,

    @SerializedName("precipType")
    val precipType: Object,

    @SerializedName("windSpeed")
    val windSpeed: Double,

    @SerializedName("windDir")
    val windDir: Double,

    @SerializedName("cloudCover")
    val cloudCover: Double,

    @SerializedName("icon")
    val icon: String

)
