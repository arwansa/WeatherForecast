package me.arwan.weatherforecast.screen.home.adapter

import me.arwan.weatherforecast.domain.model.coordinates.CoordinatesDto

interface ItemClickListener {
    fun onItemClicked(coordinate: CoordinatesDto)
}