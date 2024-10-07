package net.thebookofcode.www.amlweather.data.local.room.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "days",
    foreignKeys = [ForeignKey(
        entity = WeatherCache::class,
        parentColumns = ["weatherId"],
        childColumns = ["weatherId"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index(value = ["weatherId"])]
)
data class DayCache(
    @PrimaryKey(autoGenerate = false)
    val date: String,

    val weatherId:Long,

    val tempmax: Double,

    val tempmin: Double,

    val temp: Double,

    val feelslike: Double,

    val humidity: Double,

    val precip: Double,

    val snow: Double,

    val snowdepth: Double,

    val windspeed: Double,

    val winddir: Double,

    val icon: String,

    val cloudcover: Double,

    //val hours: List<HourCache>
)
