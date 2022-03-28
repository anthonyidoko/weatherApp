package com.example.weatherapp.di.module

import com.example.weatherapp.data.db.WeatherDatabase
import com.example.weatherapp.data.network.WeatherApiService
import com.example.weatherapp.data.repository.WeatherRepo
import com.example.weatherapp.data.repository.WeatherRepoImpl
import com.example.weatherapp.data.repository.WeatherDetailRepo
import com.example.weatherapp.data.repository.WeatherDetailRepoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun providesWeatherRepository(service: WeatherApiService, weatherDatabase: WeatherDatabase) : WeatherRepo {
        return WeatherRepoImpl(service,weatherDatabase)
    }

    @Provides
    @Singleton
    fun providesWeatherDetailRepository(service: WeatherApiService, weatherDatabase: WeatherDatabase) : WeatherDetailRepo {
        return WeatherDetailRepoImpl(service,weatherDatabase)
    }
}