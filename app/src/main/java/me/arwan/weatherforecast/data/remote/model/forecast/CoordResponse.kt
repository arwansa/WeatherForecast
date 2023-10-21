package me.arwan.weatherforecast.data.remote.model.forecast


import com.google.gson.annotations.SerializedName
import me.arwan.weatherforecast.domain.model.forecast.CoordDto

data class CoordResponse(
    @SerializedName("lat")
    val lat: Double? = 0.0,
    @SerializedName("lon")
    val lon: Double? = 0.0
) {
    fun toDto() = CoordDto(
        lat = this.lat ?: 0.0,
        lon = this.lon ?: 0.0
    )
}