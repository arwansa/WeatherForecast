package me.arwan.weatherforecast.domain.model.forecast

data class WeatherDto(
    val description: String = "",
    val icon: String = "",
    val id: Int = 0,
    val main: String = ""
)