package com.example.weatherapp.util

import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.example.weatherapp.R
import com.example.weatherapp.data.model.weather.ServiceData
import com.example.weatherapp.ui.WeatherFragment

class WeatherService : Service() {

    override fun onCreate() {
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val data = intent!!.getParcelableExtra<ServiceData>(INTENT_DATA)

        val notificationIntent = Intent(this, WeatherFragment::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this, PENDING_INTENT_REQUEST, notificationIntent,
            0
        )

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("${data?.temp?.convertTemperatureFromKelvinToCelcius(DONT_SHOW)} in ${data?.city}")
            .setContentText("Feels like ${data?.feels_like?.convertTemperatureFromKelvinToCelcius(
                DONT_SHOW)}. ${data?.description}")
            .setSmallIcon(R.drawable.ic_world)
            .setContentIntent(pendingIntent)
            .build()

        startForeground(FOREGROUND_ID, notification)

        if (data?.temp == 0.0) {
            stopSelf()
        }
        return START_REDELIVER_INTENT

    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }
}