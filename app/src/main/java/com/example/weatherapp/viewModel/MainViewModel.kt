package com.example.weatherapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.model.weather.WeatherDataResponse
import com.example.weatherapp.data.repository.WeatherRepo
import com.example.weatherapp.util.Extensions.cities
import com.example.weatherapp.util.NO_INTERNET
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: WeatherRepo,
) : ViewModel() {


    var numberOfWeatherDataInDatabase = 0
    private val _networkState = MutableLiveData<String>()
    val networkState: LiveData<String> = _networkState


    fun fetchWeatherFromApiAndSaveToRoomDatabase() {

        viewModelScope.launch {
            try {
                cities.forEach { city ->
                    val weatherResponse =
                        mainRepository.fetchWeather(city)
                    if (weatherResponse.isSuccessful) {
                        if (weatherResponse.body()?.id != null) {
                            if (numberOfWeatherDataInDatabase == 0) {
                                saveWeatherToDatabase(weatherResponse.body()!!)
                            } else {
                                val weather =
                                    mainRepository.getWeatherDataFromDatabase(weatherResponse.body()!!.id)
                                if (weather?.isFavourite == true) {

                                    weather.let {
                                        weatherResponse.body()!!.copy(
                                            base = weatherResponse.body()!!.base,
                                            clouds = weatherResponse.body()?.clouds,
                                            cod = weatherResponse.body()!!.cod,
                                            coord = weatherResponse.body()?.coord,
                                            dt = weatherResponse.body()!!.dt,
                                            id = weatherResponse.body()!!.id,
                                            main = weatherResponse.body()!!.main,
                                            name = weatherResponse.body()!!.name,
                                            sys = weatherResponse.body()!!.sys,
                                            timezone = weatherResponse.body()!!.timezone,
                                            visibility = weatherResponse.body()!!.visibility,
                                            weather = weatherResponse.body()!!.weather,
                                            wind = weatherResponse.body()!!.wind,
                                            isFavourite = true

                                        )
                                    }.let {
                                        mainRepository.updateWeatherInDatabase(
                                            it
                                        )
                                    }

                                } else {
                                    saveWeatherToDatabase(weatherResponse.body()!!)

                                }

                                val weatherDetailResponse = mainRepository.getWeatherDetail(
                                    weatherResponse.body()!!.id,
                                )
                                if (weatherDetailResponse.isSuccessful) {
                                    mainRepository.saveWeatherDetailToDatabase(
                                        weatherDetailResponse.body()!!.copy(
                                            weather_city = weatherResponse.body()!!.name,
                                            id = weatherResponse.body()!!.id
                                        )
                                    )
                                }

                            }
                        }

                    }
                }

            } catch (e: Exception) {
                if (e is IOException) {
                    _networkState.postValue(NO_INTERNET)

                } else {
                    _networkState.postValue(e.message)

                }
            }
        }

        fetchWeatherFromRoom()
    }


    fun updateWeather(weather: WeatherDataResponse) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                mainRepository.updateWeatherInDatabase(weather)

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun fetchWeatherFromRoom(): LiveData<List<WeatherDataResponse>> {
        return mainRepository.getWeatherFromDatabase()
    }

    fun searchedDatabaseForWeather(searchQuery: String): LiveData<List<WeatherDataResponse>> {
        return mainRepository.searchWeatherFromDb(searchQuery)
    }

    fun getFavouriteWeather(): LiveData<List<WeatherDataResponse>> {
        return mainRepository.getAllFavouriteWeatherFromDatabase()
    }

    private suspend fun saveWeatherToDatabase(weather: WeatherDataResponse){
        viewModelScope.launch (Dispatchers.IO){
            try {
                mainRepository.saveWeatherDataToDatabase(weather)
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }


}