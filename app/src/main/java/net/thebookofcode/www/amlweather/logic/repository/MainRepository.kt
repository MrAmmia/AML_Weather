package net.thebookofcode.www.amlweather.logic.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import net.thebookofcode.www.amlweather.data.remote.api.VisualCrossingApi
import net.thebookofcode.www.amlweather.data.local.room.dao.WeatherDao
import net.thebookofcode.www.amlweather.data.local.room.entities.CurrentConditionsCache
import net.thebookofcode.www.amlweather.data.local.room.entities.DaysCache
import net.thebookofcode.www.amlweather.data.local.room.entities.HoursCache
import net.thebookofcode.www.amlweather.data.local.room.entities.OtherWeatherCache
import net.thebookofcode.www.amlweather.logic.util.Resource
import net.thebookofcode.www.amlweather.data.util.Utilities.Companion.mapCurrentConditionToCache
import net.thebookofcode.www.amlweather.data.util.Utilities.Companion.mapDaysToCache
import net.thebookofcode.www.amlweather.data.util.Utilities.Companion.mapHoursToCache
import net.thebookofcode.www.amlweather.data.util.Utilities.Companion.mapOtherCurrentConditionToCache
import javax.inject.Inject

class MainRepository
@Inject constructor(
    private val weatherDao: WeatherDao,
    private val visualCrossingApi: VisualCrossingApi
) : MainRepositoryInterface {

    fun initiate(longNum: Double, latNum: Double): Flow<Resource<Boolean>> = flow {
        emit(Resource.Loading())
        try {
            val response = visualCrossingApi.getWeatherByLocation(longNum, latNum)
            if (response.isSuccessful) {
                response.body()?.let{ liveWeather ->
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
                    emit(Resource.Success(true))
                }

            }else{
                emit(Resource.Error("An unknown error occurred",null))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.message!!))
        }
    }

    override suspend fun getCachedCurrentConditions(): Flow<Resource<CurrentConditionsCache>> =
        flow {
            emit(Resource.Loading())
            try {
                emit(Resource.Success(weatherDao.getCurrentConditionDefaultLocation()))
            } catch (e: Exception) {
                emit(Resource.Error(e.message!!))
            }
        }

    override suspend fun getCachedCurrentConditionCount(): Int {
        return weatherDao.getCurrentConditionCount()
    }

    override suspend fun getCachedDaysCount(): Int {
        return weatherDao.getDaysCount()
    }

    override suspend fun getCachedHoursCount(): Int {
        return weatherDao.getHoursCount()
    }

    override suspend fun getCachedOthersCount(): Int {
        return weatherDao.getHoursCount()
    }

    override fun getLiveCurrentConditions(
        longNum: Double,
        latNum: Double
    ): Flow<Resource<CurrentConditionsCache>> = flow {
        emit(Resource.Loading())
        try {
            val response = visualCrossingApi.getWeatherByLocation(longNum, latNum)
            if (response.isSuccessful) {
                response.body()?.let { liveWeather ->
                    val liveCurrentConditions =
                        mapCurrentConditionToCache(liveWeather.currentConditions, liveWeather.address)
                    weatherDao.deleteCurrentCondition()
                    weatherDao.insertCurrentCondition(liveCurrentConditions)
                    emit(Resource.Success(liveCurrentConditions))
                }
            }else{
                emit(Resource.Error("An unknown error occurred",null))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.message!!))
        }

    }

    override fun getCachedDays(): Flow<Resource<List<DaysCache>>> = flow {
        emit(Resource.Loading())
        try {
            emit(Resource.Success(weatherDao.getDays()))
        } catch (e: Exception) {
            emit(Resource.Error(e.message!!))
        }
    }

    override fun getLiveDays(longNum: Double, latNum: Double): Flow<Resource<List<DaysCache>>> =
        flow {
            emit(Resource.Loading())
            try {
                val reponse = visualCrossingApi.getWeatherByLocation(longNum, latNum)
                if (reponse.isSuccessful) {
                    reponse.body()?.let { liveWeather ->
                        val liveDays = mapDaysToCache(liveWeather.days)
                        weatherDao.deleteAllDays()
                        weatherDao.insertDays(liveDays)
                        emit(Resource.Success(liveDays))
                    }
                }else{
                    emit(Resource.Error("An unknown error occurred",null))
                }
            } catch (e: Exception) {
                emit(Resource.Error(e.message!!))
            }

        }

    override fun getCachedHours(): Flow<Resource<List<HoursCache>>> = flow {
        emit(Resource.Loading())
        try {
            emit(Resource.Success(weatherDao.getHours()))
        } catch (e: Exception) {
            emit(Resource.Error(e.message!!))
        }
    }

    override fun getLiveHours(longNum: Double, latNum: Double): Flow<Resource<List<HoursCache>>> =
        flow {
            emit(Resource.Loading())
            try {
                val response = visualCrossingApi.getWeatherByLocation(longNum, latNum)
                if (response.isSuccessful) {
                    response.body()?.let { liveWeather ->
                        val liveHours = mapHoursToCache(liveWeather.days[0].hours!!)
                        weatherDao.deleteAllHours()
                        weatherDao.insertHours(liveHours)
                        emit(Resource.Success(liveHours))
                    }
                }else{
                    emit(Resource.Error("An unknown error occurred",null))
                }
            } catch (e: Exception) {
                emit(Resource.Error(e.message!!))
            }

        }

    override fun getCachedOtherWeather(): Flow<Resource<List<OtherWeatherCache>>> = flow {
        emit(Resource.Loading())
        try {
            emit(Resource.Success(weatherDao.getOthers()))
        } catch (e: Exception) {
            emit(Resource.Error(e.message!!))
        }
    }

    override fun getLiveOtherWeather(towns: ArrayList<String>): Flow<Resource<List<OtherWeatherCache>>> =
        flow {
            emit(Resource.Loading())
            try {
                val otherList = arrayListOf<OtherWeatherCache>()
                for (town in towns) {
                    val response = visualCrossingApi.getWeatherByTown(town)
                    if (response.isSuccessful) {
                        response.body()?.let { liveWeather ->
                        val otherCurrentConditions =
                            mapOtherCurrentConditionToCache(liveWeather.currentConditions, town)
                        otherList.add(otherCurrentConditions)}
                    }
                }
                weatherDao.deleteOtherWeather()
                weatherDao.insertOthersCurrentCondition(otherList)
                emit(Resource.Success(otherList))
            } catch (e: Exception) {
                emit(Resource.Error(e.message!!))
            }
        }


    override fun getOtherWeather(town: String): Flow<Resource<OtherWeatherCache>> = flow {
        emit(Resource.Loading())
        try {
            val response = visualCrossingApi.getWeatherByTown(town)
            if (response.isSuccessful) {
                response.body()?.let {  liveWeather ->
                val otherCurrentConditions =
                    mapOtherCurrentConditionToCache(liveWeather.currentConditions, town)
                weatherDao.deleteOtherWeather()
                weatherDao.insertByTown(otherCurrentConditions)
                emit(Resource.Success(otherCurrentConditions))}
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.message!!))
        }

    }
}