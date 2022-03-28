package com.example.weatherapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.weatherapp.data.db.dao.WeatherDao
import com.example.weatherapp.data.db.dao.WeatherDetailDao
import com.example.weatherapp.data.model.weather.WeatherDataResponse
import com.example.weatherapp.data.model.weatherDet.WeatherDetailResponseData
import com.example.weatherapp.util.WeatherTypeConverter

@Database(
    entities = [WeatherDataResponse::class, WeatherDetailResponseData::class], version = 1
)
@TypeConverters(WeatherTypeConverter::class)
abstract class WeatherDatabase: RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
    abstract fun weatherDetailDao(): WeatherDetailDao
}