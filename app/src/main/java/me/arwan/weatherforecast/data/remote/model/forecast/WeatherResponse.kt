package me.arwan.weatherforecast.data.remote.model.forecast


import com.google.gson.annotations.SerializedName
import me.arwan.weatherforecast.domain.model.forecast.WeatherDto

data class WeatherResponse(
    @SerializedName("description")
    val description: String? = "",
    @SerializedName("icon")
    val icon: String? = "",
    @SerializedName("id")
    val id: Int? = 0,
    @SerializedName("main")
    val main: String? = ""
) {
    fun toDto() = WeatherDto(
        description = this.description.orEmpty(),
        icon = this.icon.orEmpty(),
        id = this.id ?: 0,
        main = this.main.orEmpty()
    )
}