package net.thebookofcode.www.amlweather.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import net.thebookofcode.www.amlweather.api.VisualCrossingApi
import net.thebookofcode.www.amlweather.room.*
import net.thebookofcode.www.amlweather.util.DataState
import net.thebookofcode.www.amlweather.util.Utilities.Companion.mapCurrentConditionToCache
import net.thebookofcode.www.amlweather.util.Utilities.Companion.mapDaysToCache
import net.thebookofcode.www.amlweather.util.Utilities.Companion.mapHoursToCache
import javax.inject.Inject

class MainRepository
@Inject constructor(
    private val weatherDao: WeatherDao,
    private val visualCrossingApi: VisualCrossingApi
) {

    fun getCachedCurrentConditions(): Flow<DataState<CurrentConditionsCache>> = flow {
        emit(DataState.Loading())
        try {
            emit(DataState.Success(weatherDao.getCurrentConditionDefaultLocation()))
        }catch (e:Exception){
            emit(DataState.Error(e))
        }
    }

    fun getLiveCurrentConditions(longNum: Double,latNum: Double):Flow<DataState<CurrentConditionsCache>> =flow{
        emit(DataState.Loading())
        try{
            val liveWeather = visualCrossingApi.getWeatherByLocation(longNum, latNum)
            val liveCurrentConditions = mapCurrentConditionToCache(liveWeather.currentConditions)
            weatherDao.deleteCurrentCondition()
            weatherDao.insertCurrentCondition(liveCurrentConditions)
            weatherDao.deleteAllDays()
            weatherDao.insertDays(mapDaysToCache(liveWeather.days))
            weatherDao.deleteAllHours()
            weatherDao.insertHours(mapHoursToCache(liveWeather.days[0].hours!!))
            emit(DataState.Success(liveCurrentConditions))
        }catch (e:Exception){
            emit(DataState.Error(e))
        }

    }

    fun getCachedDays():Flow<List<DaysCache>>{
        return weatherDao.getDays()
    }

    fun getLiveDays(longNum: Double,latNum: Double):Flow<DataState<List<DaysCache>>> =flow{
        emit(DataState.Loading())
        try{
            val liveWeather = visualCrossingApi.getWeatherByLocation(longNum, latNum)
            val liveCondition = mapCurrentConditionToCache(liveWeather.currentConditions)
            val liveDays = mapDaysToCache(liveWeather.days)
            weatherDao.deleteCurrentCondition()
            weatherDao.insertCurrentCondition(liveCondition)
            weatherDao.deleteAllDays()
            weatherDao.insertDays(liveDays)
            weatherDao.deleteAllHours()
            weatherDao.insertHours(mapHoursToCache(liveWeather.days[0].hours!!))
            emit(DataState.Success(liveDays))
        }catch (e:Exception){
            emit(DataState.Error(e))
        }

    }

    fun getCachedHours():Flow<List<HoursCache>>{
        return weatherDao.getHours()
    }

    fun getLiveHours(longNum: Double,latNum: Double):Flow<DataState<List<HoursCache>>> =flow{
        emit(DataState.Loading())
        try{
            val liveWeather = visualCrossingApi.getWeatherByLocation(longNum, latNum)
            val liveDays = mapCurrentConditionToCache(liveWeather.currentConditions)
            val liveHours = mapHoursToCache(liveWeather.days[0].hours!!)
            weatherDao.deleteCurrentCondition()
            weatherDao.insertCurrentCondition(liveDays)
            weatherDao.deleteAllDays()
            weatherDao.insertDays(mapDaysToCache(liveWeather.days))
            weatherDao.deleteAllHours()
            weatherDao.insertHours(liveHours)
            emit(DataState.Success(liveHours))
        }catch (e:Exception){
            emit(DataState.Error(e))
        }

    }


    /*fun getCurrentConditionByTown(town:String) = networkBoundResource(
        query = {
            weatherDao.getCurrentConditionByTown(town)
        },
        fetch ={
            visualCrossingApi.getWeatherByTown(town).currentConditions
        },
        saveFetchResult = {
            weatherDao.deleteCurrentCondition(town)
            weatherDao.insertCurrentCondition(
                mapCurrentConditionToCache(it)
            )
        }
    )

    fun getDays(town: String) = networkBoundResource(
        query = {},
        fetch = {},
        saveFetchResult = {}
    )

    fun getWeatherByLocation(longNum: Double, latNum: Double) = networkBoundResource(
        query = {
            weatherDao.getWeatherByLocation(longNum, latNum)
        },
        fetch = {
            delay(2000)
            visualCrossingApi.getWeatherByLocation(longNum, latNum)
        },
        saveFetchResult = {
            weatherDao.deleteAllWeather()
            weatherDao.insertCurrentCondition(
                mapCurrentConditionToCache(it.currentConditions)
            )
            weatherDao.insertDays(mapDaysToCache(it.days))
            weatherDao.insertHours(mapHoursToCache(it.days[0].hours!!))
        }
    )

    fun getWeatherByTown(town: String)  = networkBoundResource(
        query = {
            weatherDao.getWeatherByTown(town)
        },
        fetch = {
            visualCrossingApi.getWeatherByTown(town)
        },
        saveFetchResult = {
            weatherDao.deleteByTown(town)
            weatherDao.insertByTown(it)
        }
    )*/


}