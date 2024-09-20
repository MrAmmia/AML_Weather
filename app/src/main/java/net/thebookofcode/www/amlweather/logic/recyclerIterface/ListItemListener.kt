package net.thebookofcode.www.amlweather.logic.recyclerIterface

import net.thebookofcode.www.amlweather.data.remote.responses.entities.OtherWeather

interface ListItemListener {
    fun onItemClick(weather: OtherWeather?)
}