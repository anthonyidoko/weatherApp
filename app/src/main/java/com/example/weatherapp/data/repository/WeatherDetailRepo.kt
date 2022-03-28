package com.example.weatherapp.data.repository

import androidx.lifecycle.LiveData
import com.example.weatherapp.data.model.weatherDet.WeatherDetailResponseData
import retrofit2.Response

interface WeatherDetailRepo {

    suspend fun getWeatherDetailFromApi(id: Int, app_id: String): Response<WeatherDetailResponseData>

    fun getWeatherDetailFromDb(weatherId: Int): LiveData<WeatherDetailResponseData>

}
