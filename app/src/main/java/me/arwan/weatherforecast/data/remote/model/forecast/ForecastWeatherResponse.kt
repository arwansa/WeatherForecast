package me.arwan.weatherforecast.data.remote.model.forecast


import com.google.gson.annotations.SerializedName
import me.arwan.weatherforecast.domain.model.forecast.CityDto
import me.arwan.weatherforecast.domain.model.forecast.ForecastWeatherDto

data class ForecastWeatherResponse(
    @SerializedName("city")
    val cityResponse: CityResponse? = CityResponse(),
    @SerializedName("cnt")
    val cnt: Int? = 0,
    @SerializedName("cod")
    val cod: String? = "",
    @SerializedName("list")
    val list: List<ForecastResponse>? = listOf(),
    @SerializedName("message")
    val message: Int? = 0
) {
    fun toDto() = ForecastWeatherDto(
        cityDto = cityResponse?.toDto() ?: CityDto(),
        cnt = this.cnt ?: 0,
        cod = this.cod.orEmpty(),
        list = this.list?.map { it.toDto() } ?: emptyList(),
        message = this.message ?: 0
    )
}