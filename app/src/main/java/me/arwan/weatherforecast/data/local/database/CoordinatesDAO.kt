package me.arwan.weatherforecast.data.local.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import me.arwan.weatherforecast.data.local.entity.CoordinatesEntity

@Dao
interface CoordinatesDAO {

    @Query("SELECT * FROM table_coordinates")
    fun getCoordinates(): Flow<List<CoordinatesEntity>>

    @Query("SELECT COUNT() FROM table_coordinates WHERE id = :id")
    fun count(id: String): Int

    @Insert
    fun insertCoordinates(coordinates: CoordinatesEntity)

    @Delete
    fun deleteCoordinates(coordinates: CoordinatesEntity)
}