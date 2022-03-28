package com.example.weatherapp.util.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.data.model.weather.WeatherDataResponse
import com.example.weatherapp.util.*
import com.example.weatherapp.util.Extensions.concatenate
import com.example.weatherapp.util.Extensions.convertTemperatureFromKelvinToCelcius
import com.example.weatherapp.util.Extensions.formatDate
import com.example.weatherapp.util.Extensions.formatTime
import com.squareup.picasso.Picasso

class WeatherAdapter : RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder>() {

    private lateinit var clickListener: OnItemClickListener
    private lateinit var favoriteClickListener: OnItemClickListener
    private val differCallBack = object : DiffUtil.ItemCallback<WeatherDataResponse>() {
        override fun areItemsTheSame(
            oldItem: WeatherDataResponse,
            newItem: WeatherDataResponse
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: WeatherDataResponse,
            newItem: WeatherDataResponse
        ): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallBack)

    inner class WeatherViewHolder(
        view: View,
        listener: OnItemClickListener,
        favoriteListener: OnItemClickListener
    ) : RecyclerView.ViewHolder(view) {
        val city: TextView = view.findViewById(R.id.tv_city_country)
        val date: TextView = view.findViewById(R.id.tv_date)
        val time: TextView = view.findViewById(R.id.tv_time)
        val temperature: TextView = view.findViewById(R.id.tv_temperature)
        val isFavourite: CheckBox = view.findViewById(R.id.favorite_box)
        val imageView: ImageView = view.findViewById(R.id.iv_weather)

        init {
            itemView.setOnClickListener {
                listener.onItemClick(differ.currentList[adapterPosition])
            }

            isFavourite.setOnClickListener {
                favoriteListener.onItemClick(differ.currentList[adapterPosition])
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.weather_item, parent, false)
        return WeatherViewHolder(view, clickListener, favoriteClickListener)
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        val currentItem = differ.currentList[position]
        val weatherIcon: String = currentItem.weather[0].icon
        holder.apply {
            city.text = concatenate(currentItem?.name,currentItem.sys.country)
            temperature.text = convertTemperatureFromKelvinToCelcius(currentItem.main.temp,true)
            date.text = formatDate(currentItem.dt)
            time.text = formatTime(currentItem.dt)
            isFavourite.isChecked = currentItem.isFavourite

            Picasso.get()
                    .load("${IMAGE_BASE_URL}$weatherIcon${IMAGE_END_URL}")
                    .into(imageView)


        }

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    interface OnItemClickListener {
        fun onItemClick(item: WeatherDataResponse)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        clickListener = listener
    }

    fun setClickListenerForFavouriteWeather(listener: OnItemClickListener) {
        favoriteClickListener = listener
    }


}