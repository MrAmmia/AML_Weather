package net.thebookofcode.www.amlweather.data.local.room.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "hours",
    foreignKeys = [ForeignKey(
        entity = DayCache::class,
        parentColumns = ["date"],
        childColumns = ["date"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class HourCache(
    //@PrimaryKey
    @PrimaryKey(autoGenerate = false)
    val time: String,

    val date:String,

    val temp: Double,

    val feelsLike: Double,

    val humidity: Double,

    val precip: Double,

    val precipProp: Double,

    val snow: Double,

    val snowDepth: Double,

    /*@SerializedName("precipType")
    val precipType: String,*/

    val windSpeed: Double,

    val windDir: Double,

    val icon: String,
) {

}
