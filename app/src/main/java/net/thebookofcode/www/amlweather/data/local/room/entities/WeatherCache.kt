package net.thebookofcode.www.amlweather.data.local.room.entities

import androidx.room.Entity
import com.google.gson.annotations.SerializedName
import net.thebookofcode.www.amlweather.data.remote.responses.entities.CurrentConditions
import net.thebookofcode.www.amlweather.data.remote.responses.entities.Days

@Entity(tableName = "weather")
data class WeatherCache(
    @SerializedName("latitude")
    val latitude: Double,

    @SerializedName("longitude")
    val longitude: Double,

    @SerializedName("resolvedAddress")
    val resolvedAddress: String,

    @SerializedName("address")
    val address: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("conditions")
    val condition: String,


    @SerializedName("timezone")
    val timezone: String,

    @SerializedName("tzoffset")
    val tzoffset: Double,

    @SerializedName("days")
    val days: List<Days>,

    @SerializedName("currentConditions")
    val currentConditions: CurrentConditions

) {
}