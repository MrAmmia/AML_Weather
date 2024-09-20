package net.thebookofcode.www.amlweather.logic.repository

import kotlinx.coroutines.flow.Flow
import net.thebookofcode.www.amlweather.data.local.room.entities.CurrentConditionsCache
import net.thebookofcode.www.amlweather.data.local.room.entities.DaysCache
import net.thebookofcode.www.amlweather.data.local.room.entities.HoursCache
import net.thebookofcode.www.amlweather.data.local.room.entities.OtherWeatherCache
import net.thebookofcode.www.amlweather.logic.util.Resource

interface MainRepositoryInterface {

    suspend fun getCachedDaysCount(): Int

    suspend fun getCachedCurrentConditionCount(): Int

    suspend fun getCachedHoursCount(): Int

    suspend fun getCachedOthersCount(): Int

    suspend fun getCachedCurrentConditions(): Flow<Resource<CurrentConditionsCache>>

    fun getLiveCurrentConditions(
        longNum: Double,
        latNum: Double
    ): Flow<Resource<CurrentConditionsCache>>

    fun getCachedDays(): Flow<Resource<List<DaysCache>>>

    fun getLiveDays(longNum: Double, latNum: Double): Flow<Resource<List<DaysCache>>>

    fun getCachedHours(): Flow<Resource<List<HoursCache>>>

    fun getLiveHours(longNum: Double, latNum: Double): Flow<Resource<List<HoursCache>>>

    fun getCachedOtherWeather():Flow<Resource<List<OtherWeatherCache>>>

    fun getLiveOtherWeather(towns: ArrayList<String>):Flow<Resource<List<OtherWeatherCache>>>

    fun getOtherWeather(town: String): Flow<Resource<OtherWeatherCache>>

}