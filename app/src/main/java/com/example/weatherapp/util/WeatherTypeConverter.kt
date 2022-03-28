package com.example.weatherapp.util

import androidx.room.TypeConverter
import com.example.weatherapp.data.model.weather.*
import com.example.weatherapp.data.model.weatherDet.City
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class WeatherTypeConverter {

    @TypeConverter
    fun fromCloudsToString(clouds: Clouds?): String?{
        return if(clouds == null) null else Gson().toJson(clouds)
    }

    @TypeConverter
    fun fromStringToClouds(value: String): Clouds{
        val result = object : TypeToken<Clouds>(){}.type
        return Gson().fromJson(value, result)
    }

    @TypeConverter
    fun fromCoordToString(coord: Coord?): String?{
        return if(coord == null) null else Gson().toJson(coord)
    }

    @TypeConverter
    fun fromStringToCoord(value: String): Coord{
        val result = object : TypeToken<Coord>(){}.type
        return Gson().fromJson(value, result)
    }

    @TypeConverter
    fun fromMainToString(main: Main?): String?{
        return if(main == null) null else Gson().toJson(main)
    }

    @TypeConverter
    fun fromStringToMain(value: String): Main{
        val result = object : TypeToken<Main>(){}.type
        return Gson().fromJson(value, result)
    }


    @TypeConverter
    fun fromWindToString(wind: Wind?): String?{
        return if(wind == null) null else Gson().toJson(wind)
    }

    @TypeConverter
    fun fromStringToWind(value: String): Wind {
        val result = object : TypeToken<Wind>(){}.type
        return Gson().fromJson(value, result)
    }


    @TypeConverter
    fun fromSysToString(sys: Sys?): String?{
        return if(sys == null) null else Gson().toJson(sys)
    }

    @TypeConverter
    fun fromStringToSys(value: String): Sys{
        val result = object : TypeToken<Sys>(){}.type
        return Gson().fromJson(value, result)
    }


    @TypeConverter
    fun fromWeatherListToString(weathers: List<Weather>?): String?{
        return if(weathers == null) null else Gson().toJson(weathers)
    }

    @TypeConverter
    fun fromStringToWeatherList(value: String): List<Weather> {
        val result = object : TypeToken<List<Weather>>(){}.type
        return Gson().fromJson(value, result)
    }

    @TypeConverter
    fun fromCity(city: City?): String?{
        return if (city == null) null else Gson().toJson(city)
    }

    @TypeConverter
    fun toCity(value: String): City{
        val result = object: TypeToken<City>(){}.type
        return Gson().fromJson(value, result)
    }

    @TypeConverter
    fun fromListOfList(list: List<com.example.weatherapp.data.model.weatherDet.List>?): String?{
        return if (list == null) null else Gson().toJson(list)
    }
    @TypeConverter
    fun toStringFromList(value: String): List<com.example.weatherapp.data.model.weatherDet.List>{
        val result = object : TypeToken<List<com.example.weatherapp.data.model.weatherDet.List>>(){}.type
        return Gson().fromJson(value, result)
    }

}