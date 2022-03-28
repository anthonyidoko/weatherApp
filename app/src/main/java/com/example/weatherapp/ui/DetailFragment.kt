package com.example.weatherapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.example.weatherapp.R
import com.example.weatherapp.data.model.weatherDet.WeatherDetailResponseData
import com.example.weatherapp.databinding.FragmentDetailBinding
import com.example.weatherapp.viewModel.WeatherDetailViewModel
import com.example.weatherapp.util.*
import com.example.weatherapp.util.Extensions.concatenate
import com.example.weatherapp.util.Extensions.convertTemperatureFromKelvinToCelcius
import com.example.weatherapp.util.Extensions.extractWeekDay
import com.example.weatherapp.util.Extensions.formatDate
import com.example.weatherapp.util.Extensions.setHumidity
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {
    private lateinit var _binding: FragmentDetailBinding
    private val binding: FragmentDetailBinding get() = _binding
    private val args: DetailFragmentArgs by navArgs()
    private val detailViewModel: WeatherDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentDetailBinding.bind(view)

        observeWeatherDetail(args.id)

    }

    private fun observeWeatherDetail(id: Int) {
        detailViewModel.getWeatherDetailFromDb(id).observe(viewLifecycleOwner, Observer {
            setWeatherDetailDataOnViews(it)
        })

    }

    private fun setWeatherDetailDataOnViews(data: WeatherDetailResponseData?) {
        when{
            data != null -> {
                binding.tvDetailNotReady.visibility = View.GONE
                binding.tvCity.text = concatenate(data.city?.name,data.city?.country)
                binding.tvDay.text = data.list?.get(0)?.dt?.let { formatDate(it) }
                binding.tvFeelsLikeValue.text = data.list?.get(0)?.main?.feels_like?.let {
                    convertTemperatureFromKelvinToCelcius(
                        it,
                        false)
                }?: ""
                binding.tvWindValue.text = String.format("${data.list?.get(0)?.wind?.speed}Km/h")

                binding.tvHumidityValue.text = setHumidity(data.list?.get(0)?.main?.humidity)

                binding.tvTypePressure.text = String.format("${data.list?.get(0)?.main?.pressure}ln")

                binding.tvVisibility.text = String.format("${data.list?.get(0)?.visibility}")

                binding.tvDewPointValue.text = ("${data.list?.get(0)?.clouds?.all}")


                binding.tvTemperatureValue.text =
                    data.list?.get(0)?.main?.temp?.let { convertTemperatureFromKelvinToCelcius(it,true) }
                        ?:""

                binding.tvDayOne.text = data.list?.get(0)?.dt?.let { extractWeekDay(it) }
                binding.tvDayTwo.text = data.list?.get(9)?.dt?.let { extractWeekDay(it) }
                binding.tvDayThree.text = data.list?.get(18)?.dt?.let { extractWeekDay(it) }
                binding.tvDayFour.text = data.list?.get(26)?.dt?.let { extractWeekDay(it) }
                binding.tvDayFive.text = data.list?.get(33)?.dt?.let { extractWeekDay(it) }
                binding.tvHumidityOne.text = setHumidity(data.list?.get(0)?.main?.humidity)
                binding.tvHumidityTwo.text = setHumidity(data.list?.get(9)?.main?.humidity)
                binding.tvHumidityThree.text =setHumidity(data.list?.get(18)?.main?.humidity)
                binding.tvHumidityFour.text = setHumidity(data.list?.get(26)?.main?.humidity)
                binding.tvHumidityFive.text = setHumidity(data.list?.get(33)?.main?.humidity)

                binding.tvWeatherDecs.text = data.list?.get(0)?.weather?.get(0)?.description ?: ""

                Picasso.get()
                    .load("${IMAGE_BASE_URL}${data.list?.get(0)?.weather?.get(0)?.icon}${IMAGE_END_URL}")
                    .into(binding.ivWeatherIcon)

                Picasso.get()
                    .load("${IMAGE_BASE_URL}${data.list?.get(0)?.weather?.get(0)?.icon}${IMAGE_END_URL}")
                    .into(binding.ivDayOne)

                Picasso.get()
                    .load("${IMAGE_BASE_URL}${data.list?.get(9)?.weather?.get(0)?.icon}${IMAGE_END_URL}")
                    .into(binding.ivDayTwo)

                Picasso.get()
                    .load("${IMAGE_BASE_URL}${data.list?.get(18)?.weather?.get(0)?.icon}${IMAGE_END_URL}")
                    .into(binding.ivDayThree)

                Picasso.get()
                    .load("${IMAGE_BASE_URL}${data.list?.get(26)?.weather?.get(0)?.icon}${IMAGE_END_URL}")
                    .into(binding.ivDayFour)

                Picasso.get()
                    .load("${IMAGE_BASE_URL}${data.list?.get(33)?.weather?.get(0)?.icon}${IMAGE_END_URL}")
                    .into(binding.ivDayFive)

            }
            else -> {
                binding.tvDetailNotReady.visibility = View.VISIBLE
            }
        }
    }


}