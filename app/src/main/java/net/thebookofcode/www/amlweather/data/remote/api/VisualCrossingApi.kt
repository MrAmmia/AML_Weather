package net.thebookofcode.www.amlweather.data.remote.api

import net.thebookofcode.www.amlweather.BuildConfig
import net.thebookofcode.www.amlweather.data.remote.responses.entities.Weather
import net.thebookofcode.www.amlweather.data.util.Constants.Companion.CITIES_ELEMENTS
import net.thebookofcode.www.amlweather.data.util.Constants.Companion.CITIES_INCLUDES
import net.thebookofcode.www.amlweather.data.util.Constants.Companion.ELEMENTS
import net.thebookofcode.www.amlweather.data.util.Constants.Companion.ICONSET
import net.thebookofcode.www.amlweather.data.util.Constants.Companion.INCLUDES
import net.thebookofcode.www.amlweather.data.util.Constants.Companion.TIMELINE
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.Response

interface VisualCrossingApi {


//    @GET("London,UK?key=$API_KEY")
//    suspend fun getWeather(): Response<Weather>

    @GET("{long},{lat}/$TIMELINE?key=${BuildConfig.API_KEY}$INCLUDES$ICONSET$ELEMENTS")
    suspend fun getWeatherByLocation(
        @Path("long") longNum: Double,
        @Path("lat") latNum: Double
    ): Response<Weather>

    @GET("{town}?key=${BuildConfig.API_KEY}$CITIES_INCLUDES$ICONSET$CITIES_ELEMENTS")
    suspend fun getWeatherByTown(
        @Path("town") town: String
    ): Response<Weather>
}