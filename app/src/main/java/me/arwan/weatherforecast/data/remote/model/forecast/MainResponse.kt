package me.arwan.weatherforecast.data.remote.model.forecast


import com.google.gson.annotations.SerializedName
import me.arwan.weatherforecast.domain.model.forecast.MainDto

data class MainResponse(
    @SerializedName("feels_like")
    val feelsLike: Double? = 0.0,
    @SerializedName("grnd_level")
    val grndLevel: Int? = 0,
    @SerializedName("humidity")
    val humidity: Int? = 0,
    @SerializedName("pressure")
    val pressure: Int? = 0,
    @SerializedName("sea_level")
    val seaLevel: Int? = 0,
    @SerializedName("temp")
    val temp: Double? = 0.0,
    @SerializedName("temp_kf")
    val tempKf: Double? = 0.0,
    @SerializedName("temp_max")
    val tempMax: Double? = 0.0,
    @SerializedName("temp_min")
    val tempMin: Double? = 0.0
) {
    fun toDto() = MainDto(
        feelsLike = this.feelsLike ?: 0.0,
        grndLevel = this.grndLevel ?: 0,
        humidity = this.humidity ?: 0,
        pressure = this.pressure ?: 0,
        seaLevel = this.seaLevel ?: 0,
        temp = this.temp ?: 0.0,
        tempKf = this.tempKf ?: 0.0,
        tempMax = this.tempMax ?: 0.0,
        tempMin = this.tempMin ?: 0.0
    )
}