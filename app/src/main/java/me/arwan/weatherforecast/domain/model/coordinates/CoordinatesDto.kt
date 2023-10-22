package me.arwan.weatherforecast.domain.model.coordinates

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CoordinatesDto(
    val id: String = "",
    val name: String = "",
    val lat: Double = 0.0,
    val lon: Double = 0.0,
    val country: String = "",
    val state: String = ""
) : Parcelable