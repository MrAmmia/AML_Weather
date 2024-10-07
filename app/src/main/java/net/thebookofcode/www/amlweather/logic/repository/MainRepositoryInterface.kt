package net.thebookofcode.www.amlweather.logic.repository

import kotlinx.coroutines.flow.Flow
import net.thebookofcode.www.amlweather.data.local.room.entities.OtherWeatherCache
import net.thebookofcode.www.amlweather.data.ui.CurrentWeatherFragmentData
import net.thebookofcode.www.amlweather.data.ui.FutureWeatherFragmentData
import net.thebookofcode.www.amlweather.logic.util.Resource

interface MainRepositoryInterface {

    suspend fun getCachedOthersCount(): Int

    suspend fun updateCachedCurrentConditions(address: String)

    suspend fun getLiveWeather(longNum: Double, latNum: Double):Flow<Resource<CurrentWeatherFragmentData>>

    suspend fun getCachedLiveWeather():Flow<Resource<CurrentWeatherFragmentData>>

    suspend fun getCachedWeatherCount(): Int

    fun getCachedDays(): Flow<Resource<FutureWeatherFragmentData>>

    fun getLiveDays(longNum: Double, latNum: Double): Flow<Resource<FutureWeatherFragmentData>>

    fun getCachedOtherWeather(): Flow<Resource<List<OtherWeatherCache>>>

    fun getLiveOtherWeather(towns: ArrayList<String>): Flow<Resource<List<OtherWeatherCache>>>

    fun getOtherWeather(town: String): Flow<Resource<OtherWeatherCache>>

}