package com.example.weatherapp.data.repository

import androidx.lifecycle.LiveData
import com.example.weatherapp.data.model.weather.WeatherDataResponse
import com.example.weatherapp.data.model.weatherDet.WeatherDetailResponseData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import retrofit2.Response

interface WeatherRepo {

    suspend fun fetchWeather(city: String): Response<WeatherDataResponse>

    suspend fun saveWeatherDataToDatabase(weather: WeatherDataResponse)

    suspend fun saveWeatherDetailToDatabase(weather: WeatherDetailResponseData)

    fun getAllFavouriteWeatherFromDatabase(): LiveData<List<WeatherDataResponse>>


    suspend fun updateWeatherInDatabase(weather: WeatherDataResponse)

    suspend fun getWeatherDataFromDatabase(id: Int): WeatherDataResponse?

    fun getWeatherFromDatabase(): LiveData<List<WeatherDataResponse>>

    fun getWeatherDetailFromDb(weatherId: Int): LiveData<WeatherDetailResponseData>

    fun searchWeatherFromDb(query: String): LiveData<List<WeatherDataResponse>>

    suspend fun getWeatherDetail(
        id: Int,
    ): Response<WeatherDetailResponseData>


}