package me.arwan.weatherforecast.data.remote.model.forecast


import com.google.gson.annotations.SerializedName
import me.arwan.weatherforecast.domain.model.forecast.RainDto

data class RainResponse(
    @SerializedName("3h")
    val h: Double? = 0.0
) {
    fun toDto() = RainDto(
        threeHours = h ?: 0.0
    )
}