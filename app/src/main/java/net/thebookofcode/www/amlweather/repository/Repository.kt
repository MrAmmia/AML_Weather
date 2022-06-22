package net.thebookofcode.www.amlweather.repository

import net.thebookofcode.www.amlweather.entity.Weather
import net.thebookofcode.www.amlweather.api.RetrofitInstance

class Repository {

    suspend fun getWeather(): Weather {
        return RetrofitInstance.api.getWeather()
    }
}