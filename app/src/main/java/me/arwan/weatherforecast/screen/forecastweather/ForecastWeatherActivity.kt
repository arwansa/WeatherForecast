package me.arwan.weatherforecast.screen.forecastweather

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import me.arwan.weatherforecast.core.Resource
import me.arwan.weatherforecast.core.showToast
import me.arwan.weatherforecast.databinding.ActivityForecastWeatherBinding
import me.arwan.weatherforecast.domain.model.coordinates.CoordinatesDto

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

        observeForecastWeather()
        loadForecastWeather()
    }

    private fun loadForecastWeather() = viewModel.loadForecastWeather(coordinate)

    private fun observeForecastWeather() = lifecycleScope.launch {
        viewModel.forecastWeatherResult.collect { result ->
            when (result.status) {
                Resource.Status.IDLE -> {}
                Resource.Status.LOADING -> {
                }

                Resource.Status.SUCCESS -> {

                }

                Resource.Status.ERROR -> {
                    showToast(result.message.orEmpty())
                }
            }
        }
    }

    companion object {
        private const val COORDINATE_KEY = "coordinate_key"
        fun startActivity(context: Context, coordinatesDto: CoordinatesDto) {
            val intent = Intent(context, ForecastWeatherActivity::class.java)
            intent.putExtra(COORDINATE_KEY, coordinatesDto)
            context.startActivity(intent)
        }
    }
}