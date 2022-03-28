package com.example.weatherapp.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.weatherapp.data.model.weather.WeatherDataResponse
import com.example.weatherapp.data.model.weatherDet.*
import com.example.weatherapp.util.Extensions.cities
import retrofit2.Response

class FakeWeatherRepository: WeatherRepo,WeatherDetailRepo {
    private val coord = Coord(lat = 35.6895,lon=139.6917)
    private val weatherCoord = com.example.weatherapp.data.model.weather.Coord(lat = 35.6895,lon=139.6917)
    private val clouds = Clouds(1)
    private val weatherCloud = com.example.weatherapp.data.model.weather.Clouds(1)
    private val main = Main(temp = 288.44,feels_like = 287.51,temp_min = 288.44,temp_max = 288.44,pressure = 1006,sea_level = 1006,grnd_level = 1004,humidity = 57,temp_kf = 0.0)
    private val weatherMain = com.example.weatherapp.data.model.weather.Main(temp = 288.44,feels_like = 287.51,temp_min = 288.44,temp_max = 288.44,pressure = 1006,sea_level = 1006,grnd_level = 1004,humidity = 57)
    private val rain = Rain(`3h` = 0.0)
    private val snow = Snow(`3h` = 0.0)
    private val sys = Sys("n")
    private val weatherSys = com.example.weatherapp.data.model.weather.Sys("n",268730,1648446311,1648491949,2)
    private val city = City(
        id= 1850144,name = "Tokyo",coord = coord,country ="JP",population = 0,timezone = 32400, sunrise = 1648413249,sunset =1648457913
    )
    private val weather = listOf(Weather("overcast clouds","04d",500,"Rain"))
    private val weatherD = listOf(com.example.weatherapp.data.model.weather.Weather("overcast clouds","04d",500,"Rain"))
    private val wind = Wind(speed = 0.62,deg = 282,gust=1.94)
    private val weatherWind = com.example.weatherapp.data.model.weather.Wind(speed = 0.62,deg = 282,gust=1.94)

    private val list = listOf(com.example.weatherapp.data.model.weatherDet.List(clouds,1648782000,"2022-03-30 18:00:00",main,0.0,rain, snow,sys,10000,weather,wind))

    private val weatherData = WeatherDataResponse("", clouds = weatherCloud,200,weatherCoord,1648461056,2332453,weatherMain,"London",weatherSys,3600,10000,weatherD,weatherWind)
    private val weatherDetail = MutableLiveData(WeatherDetailResponseData(city,40,"200",list,0,"ththtthh",2037341))
    private val weatherDataList = mutableListOf<WeatherDataResponse>()
    private val favouriteWeatherList = mutableListOf<WeatherDataResponse>()
    private val weatherDataDetailList = mutableListOf<WeatherDetailResponseData>()
    private val observableWeatherDataList = MutableLiveData<List<WeatherDataResponse>>(weatherDataList)
    private val observableWeatherDataDetailList = MutableLiveData<List<WeatherDetailResponseData>>(weatherDataDetailList)
    private val observableFavouriteWeather = MutableLiveData<List<WeatherDataResponse>>(favouriteWeatherList)
    private val observableSearchedQuery =  MutableLiveData<List<WeatherDataResponse>>(weatherDataList)
    private val emptyObservableWeatherDataList = MutableLiveData<List<WeatherDataResponse>>()
    private var shouldReturnNetworkError = false
    fun setShouldReturnNetworkError(value: Boolean){
        shouldReturnNetworkError = value
    }
    override suspend fun fetchWeather(city: String): Response<WeatherDataResponse> {
        return Response.success(weatherData)
    }

    override suspend fun saveWeatherDataToDatabase(weather: WeatherDataResponse) {
        weatherDataList.add(weather)
    }

    override suspend fun saveWeatherDetailToDatabase(weather: WeatherDetailResponseData) {
        weatherDataDetailList.add(weather)
    }

    override fun getAllFavouriteWeatherFromDatabase(): LiveData<List<WeatherDataResponse>> {
        return observableFavouriteWeather
    }

    override suspend fun updateWeatherInDatabase(weather: WeatherDataResponse) {
        for (i in weatherDataList){
            if (i.id == weather.id){
                i.isFavourite = weather.isFavourite
            }
        }
    }

    override suspend fun getWeatherDataFromDatabase(id: Int): WeatherDataResponse? {
        for (weather in weatherDataList){
            if (weather.id == id) return weather
        }
        return null
    }

    override fun getWeatherFromDatabase(): LiveData<List<WeatherDataResponse>> {
        return observableWeatherDataList
    }

    override suspend fun getWeatherDetailFromApi(
        id: Int,
        app_id: String
    ): Response<WeatherDetailResponseData> {
        TODO("Not yet implemented")
    }

    override fun getWeatherDetailFromDb(weatherId: Int): LiveData<WeatherDetailResponseData> {
        return weatherDetail
    }

    override fun searchWeatherFromDb(query: String): LiveData<List<WeatherDataResponse>> {
        return if (cities.contains(query))  observableSearchedQuery else emptyObservableWeatherDataList
    }

    override suspend fun getWeatherDetail(
        id: Int,
    ): Response<WeatherDetailResponseData> {
        TODO("Not yet implemented")
    }

    private fun refreshObservables(){
        observableWeatherDataList.postValue(weatherDataList)
        observableFavouriteWeather.postValue(favouriteWeatherList)

    }
}