package me.arwan.weatherforecast.data.local.datasource

import kotlinx.coroutines.flow.Flow
import me.arwan.weatherforecast.data.local.database.CoordinatesDAO
import me.arwan.weatherforecast.data.local.entity.CoordinatesEntity

class WeatherLocalDataSourceImpl(
    private val coordinatesDAO: CoordinatesDAO
) : WeatherLocalDataSource {

    override val coordinateList: Flow<List<CoordinatesEntity>> = coordinatesDAO.getCoordinates()

    override fun count(id: String) = coordinatesDAO.count(id)

    override fun insertCoordinates(coordinatesEntity: CoordinatesEntity) =
        coordinatesDAO.insertCoordinates(coordinatesEntity)

    override fun deleteCoordinates(coordinatesEntity: CoordinatesEntity) =
        coordinatesDAO.deleteCoordinates(coordinatesEntity)

}