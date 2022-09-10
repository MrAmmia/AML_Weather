package net.thebookofcode.www.amlweather.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import net.thebookofcode.www.amlweather.api.VisualCrossingApi
import net.thebookofcode.www.amlweather.repository.MainRepository
import net.thebookofcode.www.amlweather.room.WeatherDao
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {
    @Singleton
    @Provides
    fun provideMainRepository(
        weatherDao: WeatherDao,
        visualCrossingApi: VisualCrossingApi,
    ): MainRepository {
        return MainRepository(weatherDao, visualCrossingApi)
    }
}