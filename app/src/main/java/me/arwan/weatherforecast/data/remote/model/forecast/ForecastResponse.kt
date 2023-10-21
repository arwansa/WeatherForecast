package me.arwan.weatherforecast.data.remote.model.forecast


import com.google.gson.annotations.SerializedName
import me.arwan.weatherforecast.domain.model.forecast.CloudsDto
import me.arwan.weatherforecast.domain.model.forecast.ForecastDto
import me.arwan.weatherforecast.domain.model.forecast.MainDto
import me.arwan.weatherforecast.domain.model.forecast.RainDto
import me.arwan.weatherforecast.domain.model.forecast.SysDto
import me.arwan.weatherforecast.domain.model.forecast.WindDto

data class ForecastResponse(
    @SerializedName("clouds")
    val cloudsResponse: CloudsResponse? = CloudsResponse(),
    @SerializedName("dt")
    val dt: Int? = 0,
    @SerializedName("dt_txt")
    val dtTxt: String? = "",
    @SerializedName("main")
    val mainResponse: MainResponse? = MainResponse(),
    @SerializedName("pop")
    val pop: Double? = 0.0,
    @SerializedName("rain")
    val rainResponse: RainResponse? = RainResponse(),
    @SerializedName("sys")
    val sysResponse: SysResponse? = SysResponse(),
    @SerializedName("visibility")
    val visibility: Int? = 0,
    @SerializedName("weather")
    val weatherResponse: List<WeatherResponse>? = listOf(),
    @SerializedName("wind")
    val windResponse: WindResponse? = WindResponse()
) {
    fun toDto() = ForecastDto(
        cloudsDto = cloudsResponse?.toDto() ?: CloudsDto(),
        dt = this.dt ?: 0,
        dtTxt = dtTxt.orEmpty(),
        mainDto = mainResponse?.toDto() ?: MainDto(),
        pop = this.pop ?: 0.0,
        rainDto = rainResponse?.toDto() ?: RainDto(),
        sysDto = sysResponse?.toDto() ?: SysDto(),
        visibility = this.visibility ?: 0,
        weatherDto = weatherResponse?.map { it.toDto() } ?: emptyList(),
        windDto = windResponse?.toDto() ?: WindDto()
    )
}