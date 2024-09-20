package net.thebookofcode.www.amlweather.logic.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import net.thebookofcode.www.amlweather.data.remote.api.VisualCrossingApi
import net.thebookofcode.www.amlweather.logic.repository.MainRepository
import net.thebookofcode.www.amlweather.logic.repository.MainRepositoryInterface
import net.thebookofcode.www.amlweather.data.local.room.dao.WeatherDao
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {
    @Singleton
    @Provides
    fun provideMainRepository(
        dao: WeatherDao,
        api: VisualCrossingApi,
    )= MainRepository(dao,api) as MainRepositoryInterface
}