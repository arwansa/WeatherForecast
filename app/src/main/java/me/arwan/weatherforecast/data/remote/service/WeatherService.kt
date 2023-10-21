package me.arwan.weatherforecast.data.remote.service

import me.arwan.weatherforecast.data.remote.model.coordinates.CoordinatesResponse
import me.arwan.weatherforecast.data.remote.model.forecast.ForecastWeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    @GET("geo/1.0/direct")
    suspend fun getCoordinatesByLocationName(
        @Query("q") locationName: String,
        @Query("limit") limit: Int = 5
    ): Response<CoordinatesResponse>

    @GET("data/2.5/forecast")
    suspend fun getWeatherForecast(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double
    ): Response<ForecastWeatherResponse>

}