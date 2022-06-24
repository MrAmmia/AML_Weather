package net.thebookofcode.www.amlweather.api

import net.thebookofcode.www.amlweather.entity.Weather
import net.thebookofcode.www.amlweather.util.Constants.Companion.API_KEY
import net.thebookofcode.www.amlweather.util.Constants.Companion.ELEMENTS
import net.thebookofcode.www.amlweather.util.Constants.Companion.INCLUDES
import net.thebookofcode.www.amlweather.util.Constants.Companion.TIMELINE
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface VisualCrossingApi {

    @GET("London,UK?key=$API_KEY")
    suspend fun getWeather(): Response<Weather>

    @GET("{long},{lat}/$TIMELINE$API_KEY$INCLUDES$ELEMENTS")
    suspend fun getWeatherByLocation(
        @Path("long") longNum: Double,
        @Path("lat") latNum: Double
    ): Response<Weather>
}