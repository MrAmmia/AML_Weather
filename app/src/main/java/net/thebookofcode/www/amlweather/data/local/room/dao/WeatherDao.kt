package net.thebookofcode.www.amlweather.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import net.thebookofcode.www.amlweather.data.local.room.entities.CurrentConditionCache
import net.thebookofcode.www.amlweather.data.local.room.entities.DayCache
import net.thebookofcode.www.amlweather.data.local.room.entities.HourCache
import net.thebookofcode.www.amlweather.data.local.room.entities.OtherWeatherCache
import net.thebookofcode.www.amlweather.data.local.room.entities.WeatherCache
import net.thebookofcode.www.amlweather.data.local.room.entities.relations.WeatherWithCurrentConditionAndDay

@Dao
interface WeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeather(weather: WeatherCache)

    @Query("DELETE FROM weather")
    suspend fun deleteAllWeather()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDays(days:List<DayCache>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDay(day:DayCache)

    @Transaction
    @Query("SELECT * FROM weather LIMIT 1")
    suspend fun getWeather():WeatherWithCurrentConditionAndDay

    @Query("SELECT COUNT(*) FROM weather")
    suspend fun getWeatherCount():Int

    @Query("SELECT COUNT(*) FROM days")
    suspend fun getDaysCount():Int

    @Query("SELECT * FROM days")
    suspend fun getDays():List<DayCache>

    @Query("DELETE FROM days")
    suspend fun deleteAllDays()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHours(hours:List<HourCache>)

    @Query("SELECT * FROM hours")
    suspend fun getHours():List<HourCache>

    @Query("DELETE FROM hours")
    suspend fun deleteAllHours()

    @Query("SELECT COUNT(*) FROM hours")
    suspend fun getHoursCount():Int

    @Query("SELECT * FROM currentWeather LIMIT 1")
    suspend fun getCurrentConditionDefaultLocation(): CurrentConditionCache

    @Query("UPDATE currentWeather SET town = :address")
    suspend fun updateCurrentConditions(address: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrentCondition(weather: CurrentConditionCache)

    @Query("DELETE FROM currentWeather")
    suspend fun deleteCurrentCondition()

    @Query("SELECT COUNT(*) FROM currentWeather")
    suspend fun getCurrentConditionCount():Int


    @Query("SELECT * FROM others")
    suspend fun getOthers():List<OtherWeatherCache>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertByTown(weather: OtherWeatherCache)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOthersCurrentCondition(weather:List<OtherWeatherCache>)

    @Query("DELETE FROM others")
    suspend fun deleteOtherWeather()

    @Query("SELECT COUNT(*) FROM others")
    suspend fun getOtherCount():Int

}