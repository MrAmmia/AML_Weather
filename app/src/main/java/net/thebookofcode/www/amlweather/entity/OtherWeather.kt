package net.thebookofcode.www.amlweather.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class OtherWeather(
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

    @SerializedName("currentConditions")
    val currentConditions: CurrentConditions
) {
    var town: String? = null
}
