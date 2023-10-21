package me.arwan.weatherforecast.domain.model.forecast

data class CityDto(
    val coordDto: CoordDto = CoordDto(),
    val country: String = "",
    val id: Int = 0,
    val name: String = "",
    val population: Int = 0,
    val sunrise: Int = 0,
    val sunset: Int = 0,
    val timezone: Int = 0
)