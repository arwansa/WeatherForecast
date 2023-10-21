package me.arwan.weatherforecast.data.remote.model.forecast


import com.google.gson.annotations.SerializedName
import me.arwan.weatherforecast.domain.model.forecast.CloudsDto

data class CloudsResponse(
    @SerializedName("all")
    val all: Int? = 0
) {
    fun toDto() = CloudsDto(all = this.all ?: 0)
}