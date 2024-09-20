package net.thebookofcode.www.amlweather.data.local.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "hours")
data class HoursCache(
    //@PrimaryKey
    @PrimaryKey
    val time: String,

    val temp: Double,

    val feelslike: Double ,

    val humidity: Double ,

    val precip: Double,

    val precipProp: Double,

    val snow: Double,

    val snowdepth: Double,

    /*@SerializedName("preciptype")
    val preciptype: String,*/

    val windspeed: Double ,

    val winddir: Double,

    val icon: String,
){

}
