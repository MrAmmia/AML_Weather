package net.thebookofcode.www.amlweather.util

import net.thebookofcode.www.amlweather.entity.CurrentConditions
import net.thebookofcode.www.amlweather.entity.Days
import net.thebookofcode.www.amlweather.entity.Hours
import net.thebookofcode.www.amlweather.room.CurrentConditionsCache
import net.thebookofcode.www.amlweather.room.DaysCache
import net.thebookofcode.www.amlweather.room.HoursCache

class Utilities {
    companion object{
        fun mapCurrentConditionToCache(it: CurrentConditions): CurrentConditionsCache {
            return CurrentConditionsCache(it.datetime, it.temp,it.feelslike, it.humidity, it.precip,
                it.snow, it.snowdepth, it.condition,it.windspeed,it.winddir,it.cloudcover,it.icon)
        }

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