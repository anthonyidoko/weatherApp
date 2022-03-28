package com.example.weatherapp.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.weatherapp.data.model.weather.WeatherDataResponse
import com.example.weatherapp.data.model.weatherDet.WeatherDetailResponseData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

@Dao
interface WeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveWeather(weather: WeatherDataResponse)

    @Query("SELECT * FROM weather_table ORDER BY isFavourite DESC")
    fun getWeatherFromDatabase(): LiveData<List<WeatherDataResponse>>

//    @Query("SELECT * FROM weather_table ORDER BY isFavourite DESC")
//    fun getWeatherFromRoom(): LiveData<List<WeatherDataResponse>>

    @Query("SELECT * FROM weather_table WHERE id =:id")
    suspend fun getWeatherFromDatabase(id: Int): WeatherDataResponse?

    @Query("SELECT * FROM weather_table WHERE isFavourite =:isTrue")
    fun getFavouriteWeather(isTrue: Boolean): LiveData<List<WeatherDataResponse>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveWeatherDetail(weather: WeatherDetailResponseData)

    @Query("SELECT * FROM weather_detail_table WHERE id =:weatherId")
    fun getWeatherDetailFromDb(weatherId: Int): LiveData<WeatherDetailResponseData>

    @Query("SELECT * FROM weather_table WHERE name LIKE :query OR base LIKE :query")
    fun searchDatabaseForWeather(query: String): LiveData<List<WeatherDataResponse>>

    @Update
    suspend fun updateWeatherData(weather: WeatherDataResponse)
}