package net.thebookofcode.www.amlweather.data.remote.responses.entities

import com.google.gson.annotations.SerializedName

data class Alert(
    @SerializedName("event")
    val event: String,

    @SerializedName("description")
    val description: String,
)