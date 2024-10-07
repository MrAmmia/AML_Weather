package net.thebookofcode.www.amlweather.data.local.room.entities.relations

import androidx.room.Embedded
import androidx.room.Relation
import net.thebookofcode.www.amlweather.data.local.room.entities.CurrentConditionCache
import net.thebookofcode.www.amlweather.data.local.room.entities.DayCache
import net.thebookofcode.www.amlweather.data.local.room.entities.WeatherCache

data class WeatherWithCurrentConditionAndDay(
    @Embedded val weather: WeatherCache,

    @Relation(
        parentColumn = "weatherId",
        entityColumn = "weatherId"
    )
    val currentConditionCache: CurrentConditionCache,

    @Relation(
        parentColumn = "weatherId",
        entityColumn = "weatherId"
    )
    val days: DayCache
)
