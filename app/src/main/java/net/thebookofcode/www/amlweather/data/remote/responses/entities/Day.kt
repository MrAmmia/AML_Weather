package net.thebookofcode.www.amlweather.data.remote.responses.entities

import com.google.gson.annotations.SerializedName

data class Day(
    @SerializedName("datetime")
    val datetime: String,

    @SerializedName("tempMax")
    val tempMax: Double,

    @SerializedName("tempMin")
    val tempMin: Double,

    @SerializedName("temp")
    val temp: Double,

    @SerializedName("feelsLike")
    val feelsLike: Double,

    @SerializedName("humidity")
    val humidity: Double,

    @SerializedName("precip")
    val precip: Double,

    @SerializedName("precipType")
    val precipType: List<String>? = null,

    @SerializedName("snow")
    val snow: Double,

    @SerializedName("snowDepth")
    val snowDepth: Double,

    @SerializedName("windSpeed")
    val windSpeed: Double,

    @SerializedName("windDir")
    val winddDir: Double,

    @SerializedName("icon")
    val icon: String,

    @SerializedName("cloudCover")
    val cloudCover: Double,

    @SerializedName("hours")
    val hours: List<Hour>? = null,
)