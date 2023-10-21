package me.arwan.weatherforecast.domain.model.forecast

data class WindDto(
    val deg: Int = 0,
    val gust: Double = 0.0,
    val speed: Double = 0.0
)