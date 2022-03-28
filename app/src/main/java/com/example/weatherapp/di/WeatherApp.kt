package com.example.weatherapp.di

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import com.example.weatherapp.util.CHANNEL_ID
import com.example.weatherapp.util.CHANNEL_NAME
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class WeatherApp: Application(){

    override fun onCreate() {
        super.onCreate()

        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
             val serviceChannel = NotificationChannel(
                 CHANNEL_ID, CHANNEL_NAME,
                 NotificationManager.IMPORTANCE_DEFAULT
             )

            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(serviceChannel)
        }
    }
}