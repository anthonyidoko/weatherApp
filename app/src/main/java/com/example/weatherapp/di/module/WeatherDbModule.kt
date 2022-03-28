package com.example.weatherapp.di.module

import android.app.Application
import androidx.room.Room
import com.example.weatherapp.util.DATABASE
import com.example.weatherapp.data.db.WeatherDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
@Module
@InstallIn(SingletonComponent::class)
class WeatherDbModule {

    @Volatile
    private var INSTANCE: WeatherDatabase? = null

    @Singleton
    @Provides
    fun provideDatabase(app: Application): WeatherDatabase {
        val temInstance = INSTANCE
        if (temInstance != null) {
            return temInstance
        }

        synchronized(this) {
            val instance =
                Room.databaseBuilder(app.applicationContext, WeatherDatabase::class.java, DATABASE)
                    .build()
            INSTANCE = instance
            return instance
        }
    }
}