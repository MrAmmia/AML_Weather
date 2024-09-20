package net.thebookofcode.www.amlweather.data.remote.responses.entities

import com.google.gson.annotations.SerializedName

data class Hours(
    @SerializedName("datetime")
    val datetime: String,
    
    @SerializedName("temp")
    val temp: Double,
    
    @SerializedName("feelslike")
    val feelslike: Double ,
    
    @SerializedName("humidity")
    val humidity: Double , 
    
    @SerializedName("precip")
    val precip: Double,

    @SerializedName("precipprop")
    val precipProp: Double,
    
    @SerializedName("snow")
    val snow: Double,
    
    @SerializedName("snowdepth")
    val snowdepth: Double,
    
    /*@SerializedName("preciptype")
    val preciptype: String,*/
    
    @SerializedName("windspeed")
    val windspeed: Double ,
    
    @SerializedName("winddir")
    val winddir: Double,
    
    @SerializedName("icon")
    val icon: String,
)