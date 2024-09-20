package net.thebookofcode.www.amlweather.data.local.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "days")
data class DaysCache(
    @PrimaryKey
    val date: String,

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

)
