package me.arwan.weatherforecast.screen.forecastweather

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import me.arwan.weatherforecast.R
import me.arwan.weatherforecast.core.Resource
import me.arwan.weatherforecast.core.isDateWithinNextThreeDays
import me.arwan.weatherforecast.core.kelvinToCelsius
import me.arwan.weatherforecast.core.setGone
import me.arwan.weatherforecast.core.setVisible
import me.arwan.weatherforecast.core.showToast
import me.arwan.weatherforecast.core.toFormattedDateTime
import me.arwan.weatherforecast.databinding.ActivityForecastWeatherBinding
import me.arwan.weatherforecast.domain.model.coordinates.CoordinatesDto
import me.arwan.weatherforecast.domain.model.forecast.ForecastWeatherDto

@AndroidEntryPoint
class ForecastWeatherActivity : AppCompatActivity() {

    private val viewModel: ForecastWeatherViewModel by viewModels()

    private lateinit var binding: ActivityForecastWeatherBinding
    private lateinit var coordinate: CoordinatesDto

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForecastWeatherBinding.inflate(layoutInflater)
        setContentView(binding.root)

        coordinate = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(COORDINATE_KEY, CoordinatesDto::class.java)
        } else {
            intent.getParcelableExtra(COORDINATE_KEY)
        } ?: CoordinatesDto()

        setupLayout()
        observeCoordinateExists()
        observeForecastWeather()

        viewModel.checkCoordinateExists(coordinate.id)
        loadForecastWeather()
    }

    private fun setupLayout() = with(binding) {
        cityLabel.text = "${coordinate.name}, ${coordinate.state}, ${coordinate.country}"
        backButton.setOnClickListener { finish() }
        reloadButton.setOnClickListener { loadForecastWeather() }
        favoriteButton.setOnClickListener {
            viewModel.saveCoordinates(coordinate)
            favoriteButton.setGone()
            deleteFavoriteButton.setVisible()
        }
        deleteFavoriteButton.setOnClickListener {
            viewModel.deleteCoordinates(coordinate)
            favoriteButton.setVisible()
            deleteFavoriteButton.setGone()
        }
    }

    private fun observeCoordinateExists() = lifecycleScope.launch {
        viewModel.isCoordinateExists.collect {
            if (it) {
                binding.favoriteButton.setGone()
                binding.deleteFavoriteButton.setVisible()
            } else {
                binding.favoriteButton.setVisible()
                binding.deleteFavoriteButton.setGone()
            }
        }
    }

    private fun observeForecastWeather() = lifecycleScope.launch {
        viewModel.forecastWeatherResult.collect { result ->
            when (result.status) {
                Resource.Status.IDLE -> {}
                Resource.Status.LOADING -> {
                    binding.progressBar.setVisible()
                    binding.reloadButton.setGone()
                }

                Resource.Status.SUCCESS -> {
                    binding.progressBar.setGone()
                    binding.reloadButton.setGone()
                    result.data?.let { data ->
                        if (data.list.isNotEmpty()) {
                            showSuccessData(data)
                        }
                    }
                }

                Resource.Status.ERROR -> {
                    showToast(result.message.orEmpty())
                    binding.progressBar.setGone()
                    binding.reloadButton.setVisible()
                }
            }
        }
    }

    private fun showSuccessData(forecastWeatherDto: ForecastWeatherDto) {
        forecastWeatherDto.list.filter { it.dtTxt.isDateWithinNextThreeDays() }.forEach {
            val view = LayoutInflater.from(this).inflate(
                R.layout.item_weather, binding.containerWeather, false
            )

            val weatherIcon = view.findViewById<ImageView>(R.id.weatherIcon)
            val timeLabel = view.findViewById<TextView>(R.id.timeLabel)
            val tempLabel = view.findViewById<TextView>(R.id.temperatureLabel)
            val humidityLabel = view.findViewById<TextView>(R.id.humidityLabel)
            val windLabel = view.findViewById<TextView>(R.id.windLabel)

            Glide.with(view)
                .load("https://openweathermap.org/img/wn/${it.weatherDto.firstOrNull()?.icon}@2x.png")
                .into(weatherIcon)
            timeLabel.text = it.dtTxt.toFormattedDateTime()
            windLabel.text = "${it.windDto.speed}m/s"
            tempLabel.text = "${it.mainDto.temp.kelvinToCelsius()}°C"
            humidityLabel.text = "${it.mainDto.humidity}%"
            windLabel.text = "${it.windDto.speed}m/s"
            binding.containerWeather.addView(view)
        }
    }

    private fun loadForecastWeather() = viewModel.loadForecastWeather(coordinate)

    companion object {
        private const val COORDINATE_KEY = "coordinate_key"
        fun startActivity(context: Context, coordinatesDto: CoordinatesDto) {
            val intent = Intent(context, ForecastWeatherActivity::class.java)
            intent.putExtra(COORDINATE_KEY, coordinatesDto)
            context.startActivity(intent)
        }
    }
}