package me.arwan.weatherforecast.data.remote.model.forecast


import com.google.gson.annotations.SerializedName
import me.arwan.weatherforecast.domain.model.forecast.WindDto

data class WindResponse(
    @SerializedName("deg")
    val deg: Int? = 0,
    @SerializedName("gust")
    val gust: Double? = 0.0,
    @SerializedName("speed")
    val speed: Double? = 0.0
) {
    fun toDto() = WindDto(
        deg = this.deg ?: 0,
        gust = this.gust ?: 0.0,
        speed = this.speed ?: 0.0
    )
}