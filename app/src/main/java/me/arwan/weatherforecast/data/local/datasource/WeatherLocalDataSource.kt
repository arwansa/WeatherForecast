package me.arwan.weatherforecast.data.local.datasource

import kotlinx.coroutines.flow.Flow
import me.arwan.weatherforecast.core.Resource
import me.arwan.weatherforecast.data.local.entity.CoordinatesEntity
import me.arwan.weatherforecast.domain.model.coordinates.CoordinatesDto

interface WeatherLocalDataSource {
    val coordinateList: Flow<Resource<List<CoordinatesDto>>>
    fun count(id: String):Int
    fun insertCoordinates(coordinatesEntity: CoordinatesEntity)
    fun deleteCoordinates(coordinatesEntity: CoordinatesEntity)
}