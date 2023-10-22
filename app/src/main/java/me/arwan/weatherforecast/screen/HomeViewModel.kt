package me.arwan.weatherforecast.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import me.arwan.weatherforecast.core.Resource
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

    fun loadFavoriteLocation() = viewModelScope.launch {
        weatherRepository.coordinateList.collect {
            _favoriteLocationResult.value = it
        }
    }

    fun save()= CoroutineScope(Dispatchers.IO).launch { weatherRepository.insertCoordinates(CoordinatesDto(id = "asikasik"))}
}