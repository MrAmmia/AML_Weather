package net.thebookofcode.www.amlweather.room

import net.thebookofcode.www.amlweather.entity.CurrentConditions
import net.thebookofcode.www.amlweather.entity.Days
import net.thebookofcode.www.amlweather.entity.Weather
import net.thebookofcode.www.amlweather.util.EntityMapper
import javax.inject.Inject

class CacheMapper
@Inject
constructor():EntityMapper<WeatherCache, Weather>
{
    override fun mapFromEntity(entity: WeatherCache): Weather {
        return Weather(
            latitude = entity.latitude,
            longitude = entity.longitude,
            resolvedAddress = entity.resolvedAddress,
            address = entity.address,
            description = entity.description,
            condition = entity.condition,
            timezone = entity.timezone,
            tzoffset = entity.tzoffset,
            days = entity.days,
            currentConditions = entity.currentConditions
        )
    }

    override fun mapToEntity(domainModel: Weather): WeatherCache {
        return WeatherCache(
            latitude = domainModel.latitude,
            longitude = domainModel.longitude,
            resolvedAddress = domainModel.resolvedAddress,
            address = domainModel.address,
            description = domainModel.description,
            condition = domainModel.condition,
            timezone = domainModel.timezone,
            tzoffset = domainModel.tzoffset,
            days = domainModel.days,
            currentConditions = domainModel.currentConditions
        )
    }
}