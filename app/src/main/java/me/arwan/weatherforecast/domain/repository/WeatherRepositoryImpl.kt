package me.arwan.weatherforecast.domain.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import me.arwan.weatherforecast.data.local.datasource.WeatherLocalDataSourceImpl
import me.arwan.weatherforecast.data.local.entity.CoordinatesEntity
import me.arwan.weatherforecast.data.remote.datasource.WeatherRemoteDataSourceImpl
import me.arwan.weatherforecast.domain.model.coordinates.CoordinatesDto
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val localDataSource: WeatherLocalDataSourceImpl,
    private val remoteDataSource: WeatherRemoteDataSourceImpl
) : WeatherRepository {

    override suspend fun getCoordinatesByLocationName(locationName: String) =
        remoteDataSource.getCoordinatesByLocationName(locationName)

    override suspend fun getWeatherForecast(latitude: Double, longitude: Double) =
        remoteDataSource.getWeatherForecast(latitude, longitude)

    override val coordinateList = localDataSource.coordinateList

    override fun isCoordinatesExists(id: String) = localDataSource.count(id) > 0

    override fun insertCoordinates(coordinatesDto: CoordinatesDto) =
        localDataSource.insertCoordinates(CoordinatesEntity.fromDto(coordinatesDto))

    override fun deleteCoordinates(coordinatesDto: CoordinatesDto) =
        localDataSource.deleteCoordinates(CoordinatesEntity.fromDto(coordinatesDto))

}