package me.arwan.weatherforecast.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import me.arwan.weatherforecast.core.Resource
import me.arwan.weatherforecast.core.launchSafeIO
import me.arwan.weatherforecast.domain.model.coordinates.CoordinatesDto
import me.arwan.weatherforecast.domain.repository.WeatherRepository
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository
) : ViewModel() {
    private val _favoriteLocationResult =
        MutableStateFlow<Resource<List<CoordinatesDto>>>(Resource.idle())
    val favoriteLocationResult = _favoriteLocationResult.asStateFlow()

    private val _coordinatesResult =
        MutableStateFlow<Resource<List<CoordinatesDto>>>(Resource.idle())
    val coordinatesResult = _coordinatesResult.asStateFlow()

    private var jobRequest: Job? = null

    init {
        viewModelScope.launch {
            weatherRepository.coordinateList.collect {
                _favoriteLocationResult.value = it
            }
        }
    }

    fun getCoordinatesByLocationName(locationName: String) {
        jobRequest?.cancel()
        jobRequest = launchSafeIO(blockBefore = {
            _coordinatesResult.value = Resource.loading()
        }, blockIO = {
            val resource = weatherRepository.getCoordinatesByLocationName(locationName)
            _coordinatesResult.value = if (resource.status == Resource.Status.SUCCESS) {
                Resource.success(resource.data?.toDto().orEmpty())
            } else {
                Resource.error(resource.message.orEmpty())
            }
        }, blockException = {
            _coordinatesResult.value = Resource.error(it.localizedMessage.orEmpty())
        })
    }

    override fun onCleared() {
        jobRequest?.cancel()
        _favoriteLocationResult.value = Resource.idle()
        _coordinatesResult.value = Resource.idle()
        super.onCleared()
    }
}