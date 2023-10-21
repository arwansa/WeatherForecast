package me.arwan.weatherforecast.data.remote.model.coordinates


import com.google.gson.annotations.SerializedName
import me.arwan.weatherforecast.domain.model.coordinates.CoordinatesDto

data class CoordinatesResponseItem(
    @SerializedName("country")
    val country: String? = "",
    @SerializedName("lat")
    val lat: Double? = 0.0,
    @SerializedName("lon")
    val lon: Double? = 0.0,
    @SerializedName("name")
    val name: String? = "",
    @SerializedName("state")
    val state: String? = ""
) {
    fun toDto() = CoordinatesDto(
        id = "${this.lat}-${this.lon}",
        country = this.country.orEmpty(),
        lat = this.lat ?: 0.0,
        lon = this.lon ?: 0.0,
        name = this.name.orEmpty(),
        state = this.state.orEmpty()
    )
}