package me.arwan.weatherforecast.domain.model.forecast

data class ForecastDto(
    val cloudsDto: CloudsDto = CloudsDto(),
    val dt: Int = 0,
    val dtTxt: String = "",
    val mainDto: MainDto = MainDto(),
    val pop: Double = 0.0,
    val rainDto: RainDto = RainDto(),
    val sysDto: SysDto = SysDto(),
    val visibility: Int = 0,
    val weatherDto: List<WeatherDto> = emptyList(),
    val windDto: WindDto = WindDto()
)