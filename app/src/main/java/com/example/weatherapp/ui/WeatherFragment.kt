package com.example.weatherapp.ui

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapp.R
import com.example.weatherapp.data.model.weather.ServiceData
import com.example.weatherapp.data.model.weather.WeatherDataResponse
import com.example.weatherapp.databinding.FragmentWeatherBinding
import com.example.weatherapp.viewModel.MainViewModel
import com.example.weatherapp.util.*
import com.example.weatherapp.util.Extensions.cities
import com.example.weatherapp.util.Extensions.showViews
import com.example.weatherapp.util.adapter.WeatherAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WeatherFragment : Fragment(), SearchView.OnQueryTextListener {

    private lateinit var _binding: FragmentWeatherBinding
    private val binding: FragmentWeatherBinding get() = _binding
    private val mainViewModel: MainViewModel by activityViewModels()
    private val weatherAdapter by lazy { WeatherAdapter() }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_weather, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentWeatherBinding.bind(view)

        checkInternetConnection()
        observeWeatherFromDb()
        mainViewModel.fetchWeatherFromRoom()
        setUpRecyclerView()
        getFavouriteWeather()

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_item, menu)
        val search = menu.findItem(R.id.menu_search)
        val searchView = search.actionView as SearchView
        searchView.isSubmitButtonEnabled = true
        searchView.setOnQueryTextListener(this)
    }


    override fun onQueryTextSubmit(query: String?): Boolean {
        search(query)
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        search(query)
        return true
    }

    private fun search(query: String?) {
        if (query != null) {
            searchDatabase(query)
        }
    }

    private fun searchDatabase(query: String) {
        val searchQuery = "%$query%"
        searchDbForData(searchQuery)
    }

    private fun observeWeatherFromDb() {
        mainViewModel.fetchWeatherFromRoom().observe(viewLifecycleOwner, Observer {
            mainViewModel.numberOfWeatherDataInDatabase = it.size
            weatherAdapter.differ.submitList(it)
            when {
                it.size == cities.size -> {
                    showViews(
                        binding.tvPlsWait1,
                        binding.tvPlsWait,
                        binding.weatherPgBar,
                        binding.weatherList,
                        SHOW,
                        SHOW
                    )
                }
                it.size > 1 && it.size < cities.size -> {
                    showViews(
                        binding.tvPlsWait1,
                        binding.tvPlsWait,
                        binding.weatherPgBar,
                        binding.weatherList,
                        SHOW,
                        DONT_SHOW
                    )
                }
                else -> {
                    showViews(
                        binding.tvPlsWait1,
                        binding.tvPlsWait,
                        binding.weatherPgBar,
                        binding.weatherList,
                        DONT_SHOW,
                        DONT_SHOW
                    )

                }
            }

        })
    }

    private fun searchDbForData(searchString: String) {
        mainViewModel.searchedDatabaseForWeather(searchString)
            .observe(viewLifecycleOwner, Observer {
                weatherAdapter.differ.submitList(it)
            })
    }

    private fun setUpRecyclerView() {
        val recyclerView = binding.weatherList
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = weatherAdapter

        weatherAdapter.setOnItemClickListener(object : WeatherAdapter.OnItemClickListener {
            override fun onItemClick(item: WeatherDataResponse) {
                val action =
                    WeatherFragmentDirections.actionHomeFragmentToDetailFragment(item.id)
                findNavController().navigate(action)
            }

        })

        weatherAdapter.setClickListenerForFavouriteWeather(object :
            WeatherAdapter.OnItemClickListener {
            override fun onItemClick(item: WeatherDataResponse) {
                mainViewModel.updateWeather(item.copy(isFavourite = !item.isFavourite))
            }
        })
    }

    private fun getFavouriteWeather() {
        mainViewModel.getFavouriteWeather().observe(viewLifecycleOwner, Observer {
            val serviceIntent = Intent(activity, WeatherService::class.java)
            if (it.isNotEmpty()) {

                serviceIntent.putExtra(
                    INTENT_DATA,
                    ServiceData(it[0].main.temp, it[0].main.feels_like, it[0].weather[0].description,it[0].name)
                )
            } else {
                serviceIntent.putExtra(INTENT_DATA, ServiceData(NO_TEMP, NO_TEMP, NO_CITY))
            }
            activity?.startService(serviceIntent)
        })
    }

    private fun checkInternetConnection() {
        mainViewModel.networkState.observe(viewLifecycleOwner, Observer {
            binding.tvPlsWait.text = it
            if (it.equals(NO_INTERNET)) {
                showViews(
                    binding.tvPlsWait1,
                    binding.tvPlsWait,
                    binding.weatherPgBar,
                    binding.weatherList,
                    SHOW,
                    SHOW
                )
                binding.tvPlsWait.visibility = View.VISIBLE
            }

        })
    }




}