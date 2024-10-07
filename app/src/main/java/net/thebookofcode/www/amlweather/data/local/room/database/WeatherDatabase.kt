package net.thebookofcode.www.amlweather.data.local.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import net.thebookofcode.www.amlweather.data.local.room.dao.WeatherDao
import net.thebookofcode.www.amlweather.data.local.room.entities.CurrentConditionCache
import net.thebookofcode.www.amlweather.data.local.room.entities.DayCache
import net.thebookofcode.www.amlweather.data.local.room.entities.HourCache
import net.thebookofcode.www.amlweather.data.local.room.entities.OtherWeatherCache
import net.thebookofcode.www.amlweather.data.local.room.entities.WeatherCache

@Database(entities = [CurrentConditionCache::class, HourCache::class,
    DayCache::class, OtherWeatherCache::class, WeatherCache::class], version = 2)
abstract class WeatherDatabase:RoomDatabase() {
    abstract fun weatherDao(): WeatherDao

    companion object{
        val DATABASE_NAME:String = "weather_db"
    }
}