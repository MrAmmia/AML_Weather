package net.thebookofcode.www.amlweather.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import net.thebookofcode.www.amlweather.data.local.room.entities.CurrentConditionsCache
import net.thebookofcode.www.amlweather.data.local.room.entities.DaysCache
import net.thebookofcode.www.amlweather.data.local.room.entities.HoursCache
import net.thebookofcode.www.amlweather.data.local.room.entities.OtherWeatherCache

@Dao
interface WeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDays(days:List<DaysCache>)

    @Query("SELECT * FROM days")
    suspend fun getDays():List<DaysCache>

    @Query("DELETE FROM days")
    suspend fun deleteAllDays()

    @Query("SELECT COUNT(*) FROM days")
    suspend fun getDaysCount():Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHours(hours:List<HoursCache>)

    @Query("SELECT * FROM hours")
    suspend fun getHours():List<HoursCache>

    @Query("DELETE FROM hours")
    suspend fun deleteAllHours()

    @Query("SELECT COUNT(*) FROM hours")
    suspend fun getHoursCount():Int

    @Query("SELECT * FROM currentWeather LIMIT 1")
    suspend fun getCurrentConditionDefaultLocation(): CurrentConditionsCache

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrentCondition(weather: CurrentConditionsCache)

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