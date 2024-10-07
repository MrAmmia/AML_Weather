package net.thebookofcode.www.amlweather.data.local.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "weather")
data class WeatherCache(
    @PrimaryKey(autoGenerate = false)
    val weatherId:Long,

    @SerializedName("latitude")
    val latitude: Double,

    @SerializedName("longitude")
    val longitude: Double,

    @SerializedName("resolvedAddress")
    val resolvedAddress: String,

    @SerializedName("address")
    val address: String,

    @SerializedName("description")
    val description: String?,

    @SerializedName("conditions")
    val condition: String?,


    @SerializedName("timezone")
    val timezone: String,

    @SerializedName("tzOffset")
    val tzoffset: Double,

//    @SerializedName("days")
//    val days: List<DaysCache>,
//
//    @SerializedName("currentWeather")
//    val currentConditions: CurrentConditionsCache

) {
}