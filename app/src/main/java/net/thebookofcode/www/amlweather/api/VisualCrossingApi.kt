package net.thebookofcode.www.amlweather.api

import net.thebookofcode.www.amlweather.entity.Weather
import net.thebookofcode.www.amlweather.util.Constants.Companion.API_KEY
import retrofit2.http.GET

interface VisualCrossingApi {

    @GET("London,UK?key=$API_KEY")
    suspend fun getWeather(): Weather
}