package me.arwan.weatherforecast.data.local.datasource

import kotlinx.coroutines.flow.Flow
import me.arwan.weatherforecast.data.local.entity.CoordinatesEntity

interface WeatherLocalDataSource {
    val coordinateList: Flow<List<CoordinatesEntity>>
    fun count(id: String):Int
    fun insertCoordinates(coordinatesEntity: CoordinatesEntity)
    fun deleteCoordinates(coordinatesEntity: CoordinatesEntity)
}