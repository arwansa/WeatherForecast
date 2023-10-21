package me.arwan.weatherforecast.data.remote.model.forecast


import com.google.gson.annotations.SerializedName
import me.arwan.weatherforecast.domain.model.forecast.CityDto
import me.arwan.weatherforecast.domain.model.forecast.CoordDto

data class CityResponse(
    @SerializedName("coord")
    val coordResponse: CoordResponse? = CoordResponse(),
    @SerializedName("country")
    val country: String? = "",
    @SerializedName("id")
    val id: Int? = 0,
    @SerializedName("name")
    val name: String? = "",
    @SerializedName("population")
    val population: Int? = 0,
    @SerializedName("sunrise")
    val sunrise: Int? = 0,
    @SerializedName("sunset")
    val sunset: Int? = 0,
    @SerializedName("timezone")
    val timezone: Int? = 0
) {
    fun toDto() = CityDto(
        coordDto = coordResponse?.toDto() ?: CoordDto(),
        country = this.country.orEmpty(),
        id = this.id ?: 0,
        name = this.name.orEmpty(),
        population = this.population ?: 0,
        sunrise = this.sunrise ?: 0,
        sunset = this.sunset ?: 0,
        timezone = this.timezone ?: 0
    )
}