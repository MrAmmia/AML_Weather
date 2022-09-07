package net.thebookofcode.www.amlweather.api

import net.thebookofcode.www.amlweather.entity.Weather
import net.thebookofcode.www.amlweather.util.EntityMapper
import javax.inject.Inject

class NetworkMapper
@Inject
constructor():EntityMapper<WeatherFromApi,Weather>{
    override fun mapFromEntity(entity: WeatherFromApi): Weather {
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

    override fun mapToEntity(domainModel: Weather): WeatherFromApi {
        return WeatherFromApi(
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
