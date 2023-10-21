package me.arwan.weatherforecast.data.remote.model.forecast


import com.google.gson.annotations.SerializedName
import me.arwan.weatherforecast.domain.model.forecast.SysDto

data class SysResponse(
    @SerializedName("pod")
    val pod: String? = ""
) {
    fun toDto() = SysDto(
        pod = this.pod.orEmpty()
    )
}