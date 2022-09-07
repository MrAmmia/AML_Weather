package net.thebookofcode.www.amlweather.room

import androidx.room.Database
import androidx.room.RoomDatabase
import net.thebookofcode.www.amlweather.entity.OtherWeather
import net.thebookofcode.www.amlweather.entity.Weather

@Database(entities = [CurrentConditionsCache::class,HoursCache::class,
    DaysCache::class,OtherWeatherCache::class], version = 1)
abstract class WeatherDatabase:RoomDatabase() {
    abstract fun weatherDao():WeatherDao

    companion object{
        val DATABASE_NAME:String = "weather_db"
    }
}