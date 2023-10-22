package me.arwan.weatherforecast.screen.forecastweather

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import me.arwan.weatherforecast.core.Resource
import me.arwan.weatherforecast.core.launchSafeIO
import me.arwan.weatherforecast.domain.model.coordinates.CoordinatesDto
import me.arwan.weatherforecast.domain.model.forecast.ForecastWeatherDto
import me.arwan.weatherforecast.domain.repository.WeatherRepository
import javax.inject.Inject

@HiltViewModel
class ForecastWeatherViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository
) : ViewModel() {

    private val _forecastWeatherResult =
        MutableStateFlow<Resource<ForecastWeatherDto>>(Resource.idle())
    val forecastWeatherResult = _forecastWeatherResult.asStateFlow()

    private val _isCoordinateExists =
        MutableStateFlow<Boolean>(false)
    val isCoordinateExists = _isCoordinateExists.asStateFlow()

    private var jobRequest: Job? = null

    fun checkCoordinateExists(id: String) = CoroutineScope(Dispatchers.IO).launch {
        _isCoordinateExists.value = weatherRepository.isCoordinatesExists(id)
    }

    fun saveCoordinates(coordinatesDto: CoordinatesDto) = CoroutineScope(Dispatchers.IO).launch {
        weatherRepository.insertCoordinates(coordinatesDto)
    }

    fun deleteCoordinates(coordinatesDto: CoordinatesDto) = CoroutineScope(Dispatchers.IO).launch {
        weatherRepository.deleteCoordinates(coordinatesDto)
    }

    fun loadForecastWeather(coordinatesDto: CoordinatesDto) {
        jobRequest?.cancel()
        jobRequest = launchSafeIO(blockBefore = {
            _forecastWeatherResult.value = Resource.loading()
        }, blockIO = {
            val resource =
                weatherRepository.getWeatherForecast(coordinatesDto.lat, coordinatesDto.lon)
            _forecastWeatherResult.value =
                Resource.success(resource.data?.toDto() ?: ForecastWeatherDto())
        }, blockException = {
            _forecastWeatherResult.value = Resource.error(it.localizedMessage.orEmpty())
        })
    }

    override fun onCleared() {
        jobRequest?.cancel()
        _forecastWeatherResult.value = Resource.idle()
        super.onCleared()
    }
}