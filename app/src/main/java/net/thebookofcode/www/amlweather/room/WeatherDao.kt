package net.thebookofcode.www.amlweather.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import net.thebookofcode.www.amlweather.entity.OtherWeather
import net.thebookofcode.www.amlweather.entity.Weather

@Dao
interface WeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrentWeather(weather: CurrentConditionsCache)

    @Query("DELETE FROM currentWeather")
    suspend fun deleteAllWeather()

    @Query("SELECT * FROM currentWeather")
    fun getWeatherByLocation(): Flow<CurrentConditionsCache>

    /*
    * @Query("SELECT * FROM currentWeather")
    fun getWeatherByLocation(longNum:Double, latNum:Double): Flow<CurrentConditionsCache>
    * */

    @Query("SELECT * FROM currentWeather WHERE town = :town")
    fun getWeatherByTown(town: String):Flow<CurrentConditionsCache>

    @Query("DELETE FROM currentWeather WHERE town = :town")
    suspend fun deleteByTown(town: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertByTown(weather: CurrentConditionsCache)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDays(days:List<DaysCache>)

    @Query("SELECT * FROM days")
    fun getDays():Flow<List<DaysCache>>

    @Query("DELETE FROM days")
    suspend fun deleteAllDays()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHours(hours:List<HoursCache>)

    @Query("DELETE FROM hours")
    suspend fun deleteAllHours()

    @Query("SELECT * FROM currentWeather WHERE TOWN = :town")
    fun getCurrentConditionByTown(town: String):Flow<CurrentConditionsCache>

    @Query("SELECT * FROM currentWeather")
    fun getCurrentConditionDefaultLocation():CurrentConditionsCache

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrentCondition(weather: CurrentConditionsCache)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOthersCurrentCondition(weather:List<CurrentConditionsCache>)

    @Query("DELETE FROM currentWeather")
    suspend fun deleteCurrentCondition()

    @Query("SELECT * FROM hours")
    fun getHours():Flow<List<HoursCache>>
}