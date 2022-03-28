package com.example.weatherapp.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.example.weatherapp.data.model.weatherDet.WeatherDetailResponseData

@Dao
interface WeatherDetailDao {

    @Query("SELECT * FROM weather_detail_table WHERE id =:weatherId")
    fun getWeatherDetailFromDb(weatherId: Int): LiveData<WeatherDetailResponseData>




}