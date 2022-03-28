package com.example.weatherapp.viewModel

import com.example.weatherapp.data.repository.FakeWeatherRepository
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test

class MainViewModelTest(){

    private lateinit var viewModel: MainViewModel
    private lateinit var repository: FakeWeatherRepository

    @Before
    fun setUp(){
        repository = FakeWeatherRepository()
        viewModel = MainViewModel(repository)
    }

    @Test
    fun `get weather from room returns a not null livedata`(){
        val result = viewModel.fetchWeatherFromRoom()
        assertThat(result.value?.size != null).isTrue()
    }

    @Test
    fun `get favourite weather returns an empty list of livedata`(){
        val result = viewModel.getFavouriteWeather()
        assertThat(result.value?.size != null).isTrue()
    }

    @Test
    fun `searched database for weather returns a non empty livedata list`(){
        val result =  viewModel.searchedDatabaseForWeather("Lagos")
        assertThat(result.value?.size != null).isTrue()
    }

    @Test
    fun `searched database for weather returns an empty livedata list`(){
        val result =  viewModel.searchedDatabaseForWeather("")
        assertThat(result.value?.size != null).isFalse()
    }


}