package com.example.weatherapp.data.network

import com.example.weatherapp.data.model.weather.WeatherDataResponse
import com.example.weatherapp.data.model.weatherDet.WeatherDetailResponseData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WeatherApiService {

    @GET("weather")
    suspend fun fetchWeatherData(
        @Query("q") city: String,
        @Query("APPID") app_id: String
    ): Response<WeatherDataResponse>

    @GET("forecast")
    suspend fun fetchWeatherDetail(
        @Query("id") id: Int,
        @Query("appid") app_id: String,
    ): Response<WeatherDetailResponseData>



}