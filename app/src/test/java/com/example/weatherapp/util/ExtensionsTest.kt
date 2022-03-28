package com.example.weatherapp.util

import com.google.common.truth.Truth.assertThat
import org.junit.Test


class ExtensionsTest{

    @Test
    fun `passing a double returns a string`(){
        val result = Extensions.formatDate(1234)
        assertThat(result.isNotBlank()).isTrue()
    }

    @Test
    fun `passing a valid argument returns a string`(){
        val result = Extensions.formatTime(2222)
        assertThat(result.isNotBlank()).isTrue()
    }

    @Test
    fun `passing the boolean true returns a string ending with C `(){
        val result = Extensions.convertTemperatureFromKelvinToCelcius(21.2, true)
        assertThat(result.contains('C')).isTrue()
    }

    @Test
    fun `passing the boolean false returns a string without C `(){
        val result = Extensions.convertTemperatureFromKelvinToCelcius(21.2, false)
        assertThat(result.contains('C')).isFalse()
    }

    @Test
    fun `passing an int returns a string`(){
        val result = Extensions.setHumidity(200)
        assertThat(result.isNotEmpty()).isTrue()

    }

    @Test
    fun `passing a null value returns an empty string`(){
        val result = Extensions.setHumidity(null)
        assertThat(result.isNotEmpty()).isFalse()
    }



    @Test
    fun `passing two strings returns a string`(){
        val result = Extensions.concatenate("Hello", "World")
        assertThat(result.isNotEmpty()).isTrue()
    }

    @Test
    fun `passing a null argument and a string argument returns a string`(){
        val result = Extensions.concatenate(null, "World")
        assertThat(result.isEmpty()).isFalse()
    }
    @Test
    fun `passing two null arguments returns an empty string`(){
        val result = Extensions.concatenate(null, null)
        assertThat(result.isEmpty()).isTrue()
    }

    @Test
    fun `passing a long returns a string`(){
        val result = Extensions.extractWeekDay(2345)
        assertThat(result.isEmpty()).isFalse()
    }

}