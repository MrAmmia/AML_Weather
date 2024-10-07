package net.thebookofcode.www.amlweather.logic.repository

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import net.thebookofcode.www.amlweather.data.remote.api.VisualCrossingApi
import net.thebookofcode.www.amlweather.data.local.room.dao.WeatherDao
import net.thebookofcode.www.amlweather.data.local.room.entities.OtherWeatherCache
import net.thebookofcode.www.amlweather.data.ui.CurrentWeatherFragmentData
import net.thebookofcode.www.amlweather.data.ui.FutureWeatherFragmentData
import net.thebookofcode.www.amlweather.data.util.Constants.Companion.STALE_THRESHOLD
import net.thebookofcode.www.amlweather.data.util.Mapper.Companion.mapCurrentConditionToCache
import net.thebookofcode.www.amlweather.data.util.Mapper.Companion.mapDayToCache
import net.thebookofcode.www.amlweather.logic.util.Resource
import net.thebookofcode.www.amlweather.data.util.Mapper.Companion.mapDaysToCache
import net.thebookofcode.www.amlweather.data.util.Mapper.Companion.mapHoursToCache
import net.thebookofcode.www.amlweather.data.util.Mapper.Companion.mapOtherCurrentConditionToCache
import net.thebookofcode.www.amlweather.data.util.Mapper.Companion.mapWeatherToCache
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale
import javax.inject.Inject

class MainRepository
@Inject constructor(
    private val weatherDao: WeatherDao,
    private val visualCrossingApi: VisualCrossingApi
) : MainRepositoryInterface {

    override suspend fun updateCachedCurrentConditions(address: String) {
        weatherDao.updateCurrentConditions(address)
    }

    override suspend fun getLiveWeather(
        longNum: Double,
        latNum: Double
    ): Flow<Resource<CurrentWeatherFragmentData>> = flow {
        emit(Resource.Loading())
        try {
            val response = visualCrossingApi.getWeatherByLocation(longNum, latNum)
            if (response.isSuccessful) {
                response.body()?.let { liveWeather ->
                    val weatherId = weatherDao.getWeatherCount() + 1
                    val currentConditionsId = weatherDao.getCurrentConditionCount() + 1

                    val currentWeatherFragmentData = CurrentWeatherFragmentData(
                        weather = mapWeatherToCache(
                            liveWeather,
                            weatherId.toLong()
                        ),
                        currentConditionCache = mapCurrentConditionToCache(
                            liveWeather.currentCondition,
                            weatherId.toLong(),
                            currentConditionsId.toLong()
                        ),
                        hourCache = mapHoursToCache(
                            liveWeather.days[0].hours!!,
                            liveWeather.days[0].datetime
                        )
                    )
                    weatherDao.deleteAllWeather()
                    weatherDao.insertWeather(currentWeatherFragmentData.weather)
                    weatherDao.deleteCurrentCondition()
                    weatherDao.insertCurrentCondition(currentWeatherFragmentData.currentConditionCache)
                    weatherDao.deleteAllHours()
                    weatherDao.insertDay(mapDayToCache(liveWeather.days[0], weatherId.toLong()))
                    weatherDao.insertHours(currentWeatherFragmentData.hourCache)
                    val liveDays =
                        mapDaysToCache(
                            liveWeather.days,
                            weatherId.toLong()
                        )
                    // never uncomment this code as it will delete all hours because of ForeignKey relationship
                    // weatherDao.deleteAllDays()
                    weatherDao.insertDays(liveDays.subList(1,liveDays.size))
                    emit(Resource.Success(currentWeatherFragmentData))
                }

            } else {
                emit(Resource.Error("An unknown error occurred", null))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.message!!))
        }
    }

    override suspend fun getCachedLiveWeather(): Flow<Resource<CurrentWeatherFragmentData>> =
        flow {
            emit(Resource.Loading())
            try {
                emit(
                    Resource.Success(
                        CurrentWeatherFragmentData(
                            weatherDao.getWeather().weather,
                            weatherDao.getCurrentConditionDefaultLocation(),
                            weatherDao.getHours()
                        )
                    )
                )
            } catch (e: Exception) {
                emit(Resource.Error(e.message!!))
            }
        }

    override suspend fun getCachedWeatherCount(): Int {
        return weatherDao.getWeatherCount()
    }

    override suspend fun getCachedDaysCount(): Int {
        return weatherDao.getDaysCount()
    }

    override suspend fun isDayCacheFreshAndAvailable(): Boolean {
        val cachedDay = weatherDao.getDays().firstOrNull()
        val today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        return cachedDay != null && cachedDay.date == today
    }

    override suspend fun isWeatherCacheFresh(): Boolean {
        val currentTime = System.currentTimeMillis()
        val cachedWeather = weatherDao.getWeather()
        return currentTime - cachedWeather.weather.lastUpdated < STALE_THRESHOLD
    }


    override suspend fun getCachedOthersCount(): Int {
        return weatherDao.getHoursCount()
    }


    override fun getCachedDays(): Flow<Resource<FutureWeatherFragmentData>> = flow {
        emit(Resource.Loading())
        try {
            val days = weatherDao.getDays()
            emit(
                Resource.Success(
                    FutureWeatherFragmentData(
                        days.subList(1, days.size)
                    )
                )
            )
        } catch (e: Exception) {
            emit(Resource.Error(e.message!!))
        }
    }

    override fun getLiveDays(
        longNum: Double,
        latNum: Double
    ): Flow<Resource<FutureWeatherFragmentData>> =
        flow {
            emit(Resource.Loading())
            try {
                val reponse = visualCrossingApi.getWeatherByLocation(longNum, latNum)
                if (reponse.isSuccessful) {
                    reponse.body()?.let { liveWeather ->
                        val weatherId = weatherDao.getWeatherCount() + 1
                        val liveDays =
                            mapDaysToCache(
                                liveWeather.days,
                                weatherId.toLong()
                            )
                        weatherDao.deleteAllDays()
                        weatherDao.insertDays(liveDays)
                        emit(Resource.Success(FutureWeatherFragmentData(liveDays)))
                    }
                } else {
                    emit(Resource.Error("An unknown error occurred", null))
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
                                mapOtherCurrentConditionToCache(liveWeather.currentCondition, town)
                            otherList.add(otherCurrentConditions)
                        }
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
                response.body()?.let { liveWeather ->
                    val otherCurrentConditions =
                        mapOtherCurrentConditionToCache(liveWeather.currentCondition, town)
                    weatherDao.deleteOtherWeather()
                    weatherDao.insertByTown(otherCurrentConditions)
                    emit(Resource.Success(otherCurrentConditions))
                }
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.message!!))
        }

    }
}