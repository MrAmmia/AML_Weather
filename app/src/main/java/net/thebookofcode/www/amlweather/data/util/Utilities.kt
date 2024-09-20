package net.thebookofcode.www.amlweather.data.util

import net.thebookofcode.www.amlweather.data.remote.responses.entities.CurrentConditions
import net.thebookofcode.www.amlweather.data.remote.responses.entities.Days
import net.thebookofcode.www.amlweather.data.remote.responses.entities.Hours
import net.thebookofcode.www.amlweather.data.local.room.entities.CurrentConditionsCache
import net.thebookofcode.www.amlweather.data.local.room.entities.DaysCache
import net.thebookofcode.www.amlweather.data.local.room.entities.HoursCache
import net.thebookofcode.www.amlweather.data.local.room.entities.OtherWeatherCache

class Utilities {
    companion object{
        fun mapCurrentConditionToCache(it: CurrentConditions, town:String): CurrentConditionsCache {
            val cache = CurrentConditionsCache(it.datetime, it.temp,it.feelslike, it.humidity, it.precip,
                it.snow, it.snowdepth, it.condition,it.windspeed,it.winddir,it.cloudcover,it.icon)
            cache.town = town
            return cache
        }

        fun mapOtherCurrentConditionToCache(it: CurrentConditions, town:String): OtherWeatherCache {
            val cache = OtherWeatherCache(it.datetime, it.temp,it.feelslike, it.humidity, it.precip,
                it.snow, it.snowdepth, it.condition,it.windspeed,it.winddir,it.cloudcover,it.icon)
            cache.town = town
            return cache
        }

        /*fun mapOtherCurrentConditionsToCache(weathers: List<CurrentConditions>): List<OtherWeatherCache> {
            val arrayToReturn = arrayListOf<OtherWeatherCache>()
            for (weather in weathers){
                arrayToReturn.add(mapOtherCurrentConditionToCache(weather,weather.town))
            }
            return arrayToReturn
        }*/

        fun mapDaysToCache(days: List<Days>):List<DaysCache>{
            val arrayToReturn = arrayListOf<DaysCache>()
            for (day in days){
                arrayToReturn.add(mapDayToCache(day))
            }
            return arrayToReturn
        }

        fun mapDayToCache(day: Days): DaysCache {
            return DaysCache(
                day.datetime,day.tempmax,day.tempmin,day.temp,day.feelslike,
                day.humidity,day.precip,day.snow,day.snowdepth,
                day.windspeed,day.winddir,day.icon,day.cloudcover
            )
        }

        fun mapHoursToCache(hours: List<Hours>):List<HoursCache>{
            val arrayToReturn = arrayListOf<HoursCache>()
            for (hour in hours){
                arrayToReturn.add(mapHourToCache(hour))
            }
            return arrayToReturn
        }

        fun mapHourToCache(hour: Hours): HoursCache {
            return HoursCache(hour.datetime,hour.temp,hour.feelslike,
                hour.humidity,hour.precip,hour.precipProp,hour.snow,
                hour.snowdepth,hour.windspeed,hour.winddir,hour.icon)
        }
    }
}