package net.thebookofcode.www.amlweather.data.local.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "others")
data class OtherWeatherCache(
    @SerializedName("datetime")
    val datetime: String,

    @SerializedName("temp")
    val temp: Double,

    @SerializedName("feelsLike")
    val feelslike: Double,

    @SerializedName("humidity")
    val humidity: Double,

    @SerializedName("precip")
    val precip: Double,

    @SerializedName("snow")
    val snow: Double,

    @SerializedName("snowDepth")
    val snowdepth: Double,

    @SerializedName("conditions")
    val condition: String,

    @SerializedName("windSpeed")
    val windspeed: Double,

    @SerializedName("windDir")
    val winddir: Double,

    @SerializedName("cloudCover")
    val cloudcover: Double,

    @SerializedName("icon")
    val icon: String

){
    @PrimaryKey
    var town:String = ""
}