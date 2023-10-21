package me.arwan.weatherforecast.data.local.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import me.arwan.weatherforecast.domain.model.coordinates.CoordinatesDto

@Parcelize
@Entity(tableName = "table_coordinates")
data class CoordinatesEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val lat: Double,
    val lon: Double,
    val country: String,
    val state: String
) : Parcelable {

    fun toDto() = CoordinatesDto(
        id = this.id,
        name = this.name,
        lat = this.lat,
        lon = this.lon,
        country = this.country,
        state = this.state
    )

    companion object {
        fun fromDto(coordinates: CoordinatesDto): CoordinatesEntity = CoordinatesEntity(
            id = coordinates.id,
            name = coordinates.name,
            lat = coordinates.lat,
            lon = coordinates.lon,
            country = coordinates.country,
            state = coordinates.state
        )
    }
}