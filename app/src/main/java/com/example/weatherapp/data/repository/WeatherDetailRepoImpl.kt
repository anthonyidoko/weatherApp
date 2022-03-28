package com.example.weatherapp.data.repository

import androidx.lifecycle.LiveData
import com.example.weatherapp.data.db.WeatherDatabase
import com.example.weatherapp.data.model.weatherDet.WeatherDetailResponseData
import com.example.weatherapp.data.network.WeatherApiService
import retrofit2.Response
import javax.inject.Inject

class WeatherDetailRepoImpl @Inject constructor(
    private val service: WeatherApiService,
    private val weatherDb: WeatherDatabase
): WeatherDetailRepo {
    override suspend fun getWeatherDetailFromApi(
        id: Int,
        app_id: String
    ): Response<WeatherDetailResponseData> {
        return service.fetchWeatherDetail(id, app_id)
    }

    override fun getWeatherDetailFromDb(weatherId: Int): LiveData<WeatherDetailResponseData> {
        return weatherDb.weatherDetailDao().getWeatherDetailFromDb(weatherId)
    }
}