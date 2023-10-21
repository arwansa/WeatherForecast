package me.arwan.weatherforecast.domain.repository

import kotlinx.coroutines.flow.Flow
import me.arwan.weatherforecast.core.Resource
import me.arwan.weatherforecast.data.remote.model.coordinates.CoordinatesResponse
import me.arwan.weatherforecast.data.remote.model.forecast.ForecastWeatherResponse
import me.arwan.weatherforecast.domain.model.coordinates.CoordinatesDto

interface WeatherRepository {
    suspend fun getCoordinatesByLocationName(locationName: String): Resource<CoordinatesResponse>
    suspend fun getWeatherForecast(
        latitude: Double,
        longitude: Double
    ): Resource<ForecastWeatherResponse>

    val coordinateList: Flow<List<CoordinatesDto>>
     fun isCoordinatesExists(id: String): Boolean
     fun insertCoordinates(coordinatesDto: CoordinatesDto)
     fun deleteCoordinates(coordinatesDto: CoordinatesDto)
}