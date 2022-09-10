package net.thebookofcode.www.amlweather.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import net.thebookofcode.www.amlweather.api.VisualCrossingApi
import net.thebookofcode.www.amlweather.entity.OtherWeather
import net.thebookofcode.www.amlweather.room.*
import net.thebookofcode.www.amlweather.util.DataState
import net.thebookofcode.www.amlweather.util.Utilities.Companion.mapCurrentConditionToCache
import net.thebookofcode.www.amlweather.util.Utilities.Companion.mapDaysToCache
import net.thebookofcode.www.amlweather.util.Utilities.Companion.mapHoursToCache
import net.thebookofcode.www.amlweather.util.Utilities.Companion.mapOtherCurrentConditionToCache
import javax.inject.Inject

class MainRepository
@Inject constructor(
    private val weatherDao: WeatherDao,
    private val visualCrossingApi: VisualCrossingApi
) {
    fun initiate(longNum: Double, latNum: Double): Flow<DataState<Boolean>> = flow {
        emit(DataState.Loading())
        try {
            val liveWeather = visualCrossingApi.getWeatherByLocation(longNum, latNum)
            val liveCurrentConditions =
                mapCurrentConditionToCache(liveWeather.currentConditions, liveWeather.address)
            val liveDays = mapDaysToCache(liveWeather.days)
            val liveHours = mapHoursToCache(liveWeather.days[0].hours!!)
            weatherDao.deleteCurrentCondition()
            weatherDao.insertCurrentCondition(liveCurrentConditions)
            weatherDao.deleteAllDays()
            weatherDao.insertDays(liveDays)
            weatherDao.deleteAllHours()
            weatherDao.insertHours(liveHours)
            emit(DataState.Success(true))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    suspend fun getCachedCurrentConditions(): Flow<DataState<CurrentConditionsCache>> = flow {
        emit(DataState.Loading())
        try {
            emit(DataState.Success(weatherDao.getCurrentConditionDefaultLocation()))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    suspend fun getCachedCurrentConditionCount(): Int {
        return weatherDao.getCurrentConditionCount()
    }

    suspend fun getCachedDaysCount(): Int {
        return weatherDao.getDaysCount()
    }

    suspend fun getCachedHoursCount(): Int {
        return weatherDao.getHoursCount()
    }

    suspend fun getCachedOthersCount(): Int {
        return weatherDao.getHoursCount()
    }

    fun getLiveCurrentConditions(
        longNum: Double,
        latNum: Double
    ): Flow<DataState<CurrentConditionsCache>> = flow {
        emit(DataState.Loading())
        try {
            val liveWeather = visualCrossingApi.getWeatherByLocation(longNum, latNum)
            val liveCurrentConditions =
                mapCurrentConditionToCache(liveWeather.currentConditions, liveWeather.address)
            weatherDao.deleteCurrentCondition()
            weatherDao.insertCurrentCondition(liveCurrentConditions)
            emit(DataState.Success(liveCurrentConditions))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }

    }

    fun getCachedDays(): Flow<DataState<List<DaysCache>>> = flow {
        emit(DataState.Loading())
        try {
            emit(DataState.Success(weatherDao.getDays()))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    fun getLiveDays(longNum: Double, latNum: Double): Flow<DataState<List<DaysCache>>> = flow {
        emit(DataState.Loading())
        try {
            val liveWeather = visualCrossingApi.getWeatherByLocation(longNum, latNum)
            val liveDays = mapDaysToCache(liveWeather.days)
            weatherDao.deleteAllDays()
            weatherDao.insertDays(liveDays)
            emit(DataState.Success(liveDays))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }

    }

    fun getCachedHours(): Flow<DataState<List<HoursCache>>> = flow {
        emit(DataState.Loading())
        try {
            emit(DataState.Success(weatherDao.getHours()))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    fun getLiveHours(longNum: Double, latNum: Double): Flow<DataState<List<HoursCache>>> = flow {
        emit(DataState.Loading())
        try {
            val liveWeather = visualCrossingApi.getWeatherByLocation(longNum, latNum)
            val liveHours = mapHoursToCache(liveWeather.days[0].hours!!)
            weatherDao.deleteAllHours()
            weatherDao.insertHours(liveHours)
            emit(DataState.Success(liveHours))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }

    }

    fun getCachedOtherWeather():Flow<DataState<List<OtherWeatherCache>>> = flow{
        emit(DataState.Loading())
        try{
            emit(DataState.Success(weatherDao.getOthers()))
        }catch (e:Exception){
            emit(DataState.Error(e))
        }
    }

    fun getLiveOtherWeather(towns: ArrayList<String>):Flow<DataState<List<OtherWeatherCache>>> = flow{
        emit(DataState.Loading())
        try{
            val otherList = arrayListOf<OtherWeatherCache>()
            for (town in towns){
                val liveWeather = visualCrossingApi.getWeatherByTown(town)
                val otherCurrentConditions =
                    mapOtherCurrentConditionToCache(liveWeather.currentConditions, town)
                otherList.add(otherCurrentConditions)
            }
            weatherDao.deleteOtherWeather()
            weatherDao.insertOthersCurrentCondition(otherList)
            emit(DataState.Success(otherList))
        }catch (e:Exception){
            emit(DataState.Error(e))
        }
    }


    fun getOtherWeather(town: String): Flow<DataState<OtherWeatherCache>> = flow {
        emit(DataState.Loading())
        try {
            val liveWeather = visualCrossingApi.getWeatherByTown(town)
            val otherCurrentConditions =
                mapOtherCurrentConditionToCache(liveWeather.currentConditions, town)
            weatherDao.deleteOtherWeather()
            weatherDao.insertByTown(otherCurrentConditions)
            emit(DataState.Success(otherCurrentConditions))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }

    }
}