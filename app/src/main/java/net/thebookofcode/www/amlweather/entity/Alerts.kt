package net.thebookofcode.www.amlweather.entity

import com.google.gson.annotations.SerializedName

data class Alerts(
    @SerializedName("event")
    val event: String,

    @SerializedName("description")
    val description: String,
)