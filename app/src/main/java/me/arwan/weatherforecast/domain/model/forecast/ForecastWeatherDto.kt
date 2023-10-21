package me.arwan.weatherforecast.domain.model.forecast

data class ForecastWeatherDto(
    val cityDto: CityDto = CityDto(),
    val cnt: Int = 0,
    val cod: String = "",
    val list: List<ForecastDto> = emptyList(),
    val message: Int = 0
)