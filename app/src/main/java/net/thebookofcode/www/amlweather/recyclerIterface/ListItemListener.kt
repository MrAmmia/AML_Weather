package net.thebookofcode.www.amlweather.recyclerIterface

import net.thebookofcode.www.amlweather.entity.OtherWeather
import net.thebookofcode.www.amlweather.entity.Weather

interface ListItemListener {
    fun onItemClick(weather: OtherWeather?)
}