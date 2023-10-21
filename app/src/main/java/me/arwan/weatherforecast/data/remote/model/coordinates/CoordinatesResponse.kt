package me.arwan.weatherforecast.data.remote.model.coordinates


class CoordinatesResponse : ArrayList<CoordinatesResponseItem>() {
    fun toDto() = this.map { it.toDto() }
}