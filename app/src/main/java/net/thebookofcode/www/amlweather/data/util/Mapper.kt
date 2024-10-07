package net.thebookofcode.www.amlweather.data.util

import net.thebookofcode.www.amlweather.data.remote.responses.entities.CurrentCondition
import net.thebookofcode.www.amlweather.data.remote.responses.entities.Day
import net.thebookofcode.www.amlweather.data.remote.responses.entities.Hour
import net.thebookofcode.www.amlweather.data.local.room.entities.CurrentConditionCache
import net.thebookofcode.www.amlweather.data.local.room.entities.DayCache
import net.thebookofcode.www.amlweather.data.local.room.entities.HourCache
import net.thebookofcode.www.amlweather.data.local.room.entities.OtherWeatherCache
import net.thebookofcode.www.amlweather.data.local.room.entities.WeatherCache
import net.thebookofcode.www.amlweather.data.remote.responses.entities.Weather

class Mapper {
    companion object {
        fun mapCurrentConditionToCache(
            it: CurrentCondition,
            weatherId: Long,
            currentConditionsId: Long
        ): CurrentConditionCache {
            val cache = CurrentConditionCache(
                currentConditionsId,
                weatherId,
                it.datetime,
                it.temp,
                it.feelsLike,
                it.humidity,
                it.precip,
                it.snow,
                it.snowDepth,
                it.condition,
                it.windSpeed,
                it.windDir,
                it.cloudCover,
                it.icon
            )
            return cache
        }

        fun mapOtherCurrentConditionToCache(
            it: CurrentCondition,
            town: String
        ): OtherWeatherCache {
            val cache = OtherWeatherCache(
                it.datetime,
                it.temp,
                it.feelsLike,
                it.humidity,
                it.precip,
                it.snow,
                it.snowDepth,
                it.condition,
                it.windSpeed,
                it.windDir,
                it.cloudCover,
                it.icon
            )
            cache.town = town
            return cache
        }

        fun mapDaysToCache(days: List<Day>, weatherId: Long): List<DayCache> {
            val arrayToReturn = arrayListOf<DayCache>()
            for (day in days) {
                arrayToReturn.add(mapDayToCache(day, weatherId))
            }
            return arrayToReturn
        }

        fun mapDayToCache(day: Day, weatherId: Long): DayCache {
            return DayCache(
                day.datetime, weatherId, day.tempMax, day.tempMin, day.temp, day.feelsLike,
                day.humidity, day.precip, day.snow, day.snowDepth,
                day.windSpeed, day.winddDir, day.icon, day.cloudCover
            )
        }

        fun mapHoursToCache(hours: List<Hour>, date: String): List<HourCache> {
            val arrayToReturn = arrayListOf<HourCache>()
            for (hour in hours) {
                arrayToReturn.add(mapHourToCache(hour, date))
            }
            return arrayToReturn
        }

        fun mapHourToCache(hour: Hour, date: String): HourCache {
            return HourCache(
                hour.datetime, date, hour.temp, hour.feelsLike,
                hour.humidity, hour.precip, hour.precipProp, hour.snow,
                hour.snowDepth, hour.windSpeed, hour.windDir, hour.icon
            )
        }

        fun mapWeatherToCache(
            weather: Weather,
            weatherId: Long
        ): WeatherCache {
            return WeatherCache(
                weatherId = weatherId,
                latitude = weather.latitude,
                longitude = weather.longitude,
                address = weather.address,
                resolvedAddress = weather.resolvedAddress,
                description = weather.description,
                condition = weather.condition,
                timezone = weather.timezone,
                tzoffset = weather.tzOffset,
            )
        }
    }
}