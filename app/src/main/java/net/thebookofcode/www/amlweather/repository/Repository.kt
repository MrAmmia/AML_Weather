package net.thebookofcode.www.amlweather.repository

import net.thebookofcode.www.amlweather.entity.Weather
import net.thebookofcode.www.amlweather.api.RetrofitInstance
import retrofit2.Response

class Repository {

    suspend fun getWeather(): Response<Weather> {
        return RetrofitInstance.api.getWeather()
    }

    suspend fun getWeatherByLocation(longNum: Double, latNum: Double): Response<Weather> {
        return RetrofitInstance.api.getWeatherByLocation(longNum, latNum)
    }

    suspend fun getWeatherByTown(town: String): Response<Weather> {
        return RetrofitInstance.api.getWeatherByTown(town)
    }
}