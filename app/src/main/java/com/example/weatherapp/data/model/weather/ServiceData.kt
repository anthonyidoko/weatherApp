package com.example.weatherapp.data.model.weather

import android.os.Parcelable
import com.example.weatherapp.util.NO_CITY
import com.example.weatherapp.util.NO_TEMP
import kotlinx.parcelize.Parcelize

@Parcelize
data class ServiceData(
    val temp: Double? = NO_TEMP,
    val feels_like: Double? = NO_TEMP,
    val description: String? = NO_CITY,
    val city: String? = NO_CITY,

):Parcelable
