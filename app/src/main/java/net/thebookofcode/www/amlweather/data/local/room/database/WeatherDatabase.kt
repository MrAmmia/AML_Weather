package net.thebookofcode.www.amlweather.data.local.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import net.thebookofcode.www.amlweather.data.local.room.dao.WeatherDao
import net.thebookofcode.www.amlweather.data.local.room.entities.CurrentConditionsCache
import net.thebookofcode.www.amlweather.data.local.room.entities.DaysCache
import net.thebookofcode.www.amlweather.data.local.room.entities.HoursCache
import net.thebookofcode.www.amlweather.data.local.room.entities.OtherWeatherCache

@Database(entities = [CurrentConditionsCache::class, HoursCache::class,
    DaysCache::class, OtherWeatherCache::class], version = 1)
abstract class WeatherDatabase:RoomDatabase() {
    abstract fun weatherDao(): WeatherDao

    companion object{
        val DATABASE_NAME:String = "weather_db"
    }
}