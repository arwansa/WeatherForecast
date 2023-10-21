package me.arwan.weatherforecast.data.remote.datasource

import me.arwan.weatherforecast.data.remote.service.WeatherService
import javax.inject.Inject

class WeatherRemoteDataSourceImpl @Inject constructor(
    private val weatherService: WeatherService
) : BaseDataSource(), WeatherRemoteDataSource {

    override suspend fun getCoordinatesByLocationName(locationName: String) = getResult {
        weatherService.getCoordinatesByLocationName(locationName)
    }

    override suspend fun getWeatherForecast(latitude: Double, longitude: Double) = getResult {
        weatherService.getWeatherForecast(latitude, longitude)
    }
}