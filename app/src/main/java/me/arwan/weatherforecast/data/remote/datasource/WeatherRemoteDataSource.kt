package me.arwan.weatherforecast.data.remote.datasource

import me.arwan.weatherforecast.core.Resource
import me.arwan.weatherforecast.data.remote.model.coordinates.CoordinatesResponse
import me.arwan.weatherforecast.data.remote.model.forecast.ForecastWeatherResponse

interface WeatherRemoteDataSource {
    suspend fun getCoordinatesByLocationName(locationName: String): Resource<CoordinatesResponse>
    suspend fun getWeatherForecast(
        latitude: Double,
        longitude: Double
    ): Resource<ForecastWeatherResponse>
}