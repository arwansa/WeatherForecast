package me.arwan.weatherforecast.data.local.datasource

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import me.arwan.weatherforecast.core.Resource
import me.arwan.weatherforecast.data.local.database.CoordinatesDAO
import me.arwan.weatherforecast.data.local.entity.CoordinatesEntity
import javax.inject.Inject

class WeatherLocalDataSourceImpl @Inject constructor(
    private val coordinatesDAO: CoordinatesDAO
) : WeatherLocalDataSource {

    override val coordinateList = flow {
        coordinatesDAO.getCoordinates().collect {
            emit(Resource.success(it.map { coordinatesEntity ->
                coordinatesEntity.toDto()
            }))
        }
    }.catch {
        emit(Resource.error(it.localizedMessage.orEmpty()))
    }.flowOn(Dispatchers.IO)

    override fun count(id: String) = coordinatesDAO.count(id)

    override fun insertCoordinates(coordinatesEntity: CoordinatesEntity) =
        coordinatesDAO.insertCoordinates(coordinatesEntity)

    override fun deleteCoordinates(coordinatesEntity: CoordinatesEntity) =
        coordinatesDAO.deleteCoordinates(coordinatesEntity)

}