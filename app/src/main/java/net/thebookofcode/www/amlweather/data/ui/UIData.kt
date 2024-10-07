package net.thebookofcode.www.amlweather.data.ui

import net.thebookofcode.www.amlweather.data.local.room.entities.CurrentConditionCache
import net.thebookofcode.www.amlweather.data.local.room.entities.DayCache
import net.thebookofcode.www.amlweather.data.local.room.entities.HourCache
import net.thebookofcode.www.amlweather.data.local.room.entities.WeatherCache

data class CurrentWeatherFragmentData(
    val weather: WeatherCache,
    val currentConditionCache: CurrentConditionCache,
    val hourCache: List<HourCache>
)

data class FutureWeatherFragmentData(
    val days:List<DayCache>
)
