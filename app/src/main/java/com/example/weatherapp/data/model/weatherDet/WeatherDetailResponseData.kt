package com.example.weatherapp.data.model.weatherDet

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather_detail_table")
data class WeatherDetailResponseData(
    val city: City?,
    val cnt: Int,
    val cod: String,
    val list: kotlin.collections.List<List>?,
    val message: Int,
    val weather_city: String?,
    @PrimaryKey
    val id: Int
)