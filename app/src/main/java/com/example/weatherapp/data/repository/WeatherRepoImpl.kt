package com.example.weatherapp.data.repository

import androidx.lifecycle.LiveData
import com.example.weatherapp.data.db.WeatherDatabase
import com.example.weatherapp.data.model.weather.WeatherDataResponse
import com.example.weatherapp.data.model.weatherDet.WeatherDetailResponseData
import com.example.weatherapp.data.network.WeatherApiService
import retrofit2.Response
import javax.inject.Inject

class WeatherRepoImpl @Inject constructor(
    private val service: WeatherApiService,
    weatherDb: WeatherDatabase
): WeatherRepo {

    companion object{
        init {
            System.loadLibrary("weatherapp")
        }
    }

    external fun getApiKey(): String

    private val weatherDatabase = weatherDb.weatherDao()

    override suspend fun fetchWeather(city: String): Response<WeatherDataResponse> {
        return service.fetchWeatherData(city,getApiKey())
    }

    override suspend fun getWeatherDetail(
        id: Int
    ): Response<WeatherDetailResponseData> {
        return service.fetchWeatherDetail(id, getApiKey())
    }


    override suspend fun saveWeatherDataToDatabase(weather: WeatherDataResponse) {
        weatherDatabase.saveWeather(weather)
    }

    override suspend fun saveWeatherDetailToDatabase(weather: WeatherDetailResponseData) {
        weatherDatabase.saveWeatherDetail(weather)
    }

    override fun getAllFavouriteWeatherFromDatabase(): LiveData<List<WeatherDataResponse>> {
        return weatherDatabase.getFavouriteWeather(true)
    }

    override suspend fun updateWeatherInDatabase(weather: WeatherDataResponse) {
        weatherDatabase.updateWeatherData(weather)
    }

    override fun getWeatherFromDatabase(): LiveData<List<WeatherDataResponse>> {
        return weatherDatabase.getWeatherFromDatabase()
    }

    override suspend fun getWeatherDataFromDatabase(id: Int): WeatherDataResponse? {
        return weatherDatabase.getWeatherFromDatabase(id)
    }

    override fun getWeatherDetailFromDb(weatherId: Int): LiveData<WeatherDetailResponseData> {
        return weatherDatabase.getWeatherDetailFromDb(weatherId)
    }

    override fun searchWeatherFromDb(query: String): LiveData<List<WeatherDataResponse>> {
        return weatherDatabase.searchDatabaseForWeather(query)
    }

}