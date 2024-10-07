package net.thebookofcode.www.amlweather.data.local.room.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(
    tableName = "currentWeather",
    foreignKeys = [ForeignKey(
        entity = WeatherCache::class,
        parentColumns = ["weatherId"],
        childColumns = ["weatherId"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index(value = ["weatherId"])]
)
data class CurrentConditionCache(
    @PrimaryKey//(autoGenerate = true)
    val currentConditionId: Long,

    val weatherId: Long,

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

    @SerializedName("windSpeed")
    val windSpeed: Double,

    @SerializedName("windDir")
    val windDir: Double,

    @SerializedName("cloudCover")
    val cloudCover: Double,

    @SerializedName("icon")
    val icon: String,

    ) {
    var town: String = ""
}

