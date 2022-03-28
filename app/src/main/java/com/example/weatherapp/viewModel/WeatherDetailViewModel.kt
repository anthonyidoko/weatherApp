package com.example.weatherapp.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.model.weatherDet.WeatherDetailResponseData
import com.example.weatherapp.data.repository.WeatherDetailRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class WeatherDetailViewModel @Inject constructor(
    private val repo: WeatherDetailRepo
): ViewModel() {

    fun getWeatherDetailFromDb(id: Int): LiveData<WeatherDetailResponseData> {
        return repo.getWeatherDetailFromDb(id)
    }
}