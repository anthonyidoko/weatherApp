package com.example.weatherapp.util

import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*

//fun formatDate(value: Long): String {
//    return SimpleDateFormat("E, dd MMMM, yyyy", Locale.getDefault()).format(Date(value * 1000))
//}

fun Long.formatDate(): String{
    return SimpleDateFormat("E, dd MMMM, yyyy", Locale.getDefault()).format(Date(this * 1000))
}

fun Long.formatTime(): String{
    return SimpleDateFormat("hh:mm a", Locale.getDefault()).format(Date(this * 1000)).uppercase()
}

//fun formatTime(value: Long): String {
//    return SimpleDateFormat("hh:mm a", Locale.getDefault()).format(Date(value * 1000)).uppercase()
//}

fun Double.convertTemperatureFromKelvinToCelcius(hasUnit: Boolean): String {
    return if (hasUnit) "${this.toInt() - TEMPERATURE_UNIT}°C" else "${this.toInt() - TEMPERATURE_UNIT}°"
}

fun Int?.setHumidity(): String {
    return if (this != null) "${this}%" else ""
}

fun String?.concatenate(secondString: String?): String {
    val result = if (this != null && secondString != null) {
        "$this, $secondString"
    } else if (this != null) {
        "$this"
    } else if (secondString != null) {
        "$secondString"
    } else {
        ""
    }
    return result
}

fun Long.extractDate() :String{
    return SimpleDateFormat("EEEE", Locale.getDefault()).format(Date(this * 1000))

}
fun Long.extractWeekDay(): String {
    return SimpleDateFormat("EEEE", Locale.getDefault()).format(Date(this * 1000))
}


val cities = listOf(
    "Lagos",
    "London",
    "Paris",
    "Beijing",
    "Delhi",
    "Pretoria",
    "Dakar",
    "Dubai",
    "California",
    "Istanbul",
    "Karachi",
    "Florence",
    "Cairo",
    "Kumasi",
    "Ontario",
    "Toronto",
    "Atlanta",
    "Tokyo",
    "Tunis",
    "Kinshasa",
)

fun showViews(
    message: TextView,
    message2: TextView,
    pgBar: ProgressBar,
    recyclerView: RecyclerView,
    show: Boolean,
    showCompletely: Boolean
) {
    if (show && showCompletely) {
        recyclerView.visibility = View.VISIBLE
        message.visibility = View.GONE
        message2.visibility = View.GONE
        pgBar.visibility = View.GONE
    } else if (show) {
        recyclerView.visibility = View.VISIBLE
        message.visibility = View.GONE
        message2.visibility = View.VISIBLE
        pgBar.visibility = View.GONE
    } else {
        recyclerView.visibility = View.GONE
        message.visibility = View.VISIBLE
        message2.visibility = View.GONE
        pgBar.visibility = View.VISIBLE
    }

}



