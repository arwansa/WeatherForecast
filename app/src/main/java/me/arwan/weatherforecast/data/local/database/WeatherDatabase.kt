package me.arwan.weatherforecast.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import me.arwan.weatherforecast.data.local.entity.CoordinatesEntity

@Database(
    entities = [CoordinatesEntity::class],
    version = 1,
    exportSchema = false
)
abstract class WeatherDataBase : RoomDatabase() {
    abstract fun getCoordinatesDao(): CoordinatesDAO
}