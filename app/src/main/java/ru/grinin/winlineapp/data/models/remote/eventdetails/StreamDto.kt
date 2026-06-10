package ru.grinin.winlineapp.data.models.remote.eventdetails

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StreamDto(
    @SerialName("controls")
    val controls: Boolean? = null,

    @SerialName("fixedSize")
    val fixedSize: Boolean? = null,

    @SerialName("fullscreen")
    val fullscreen: Boolean? = null,

    @SerialName("streamType")
    val streamType: String? = null,

    @SerialName("description")
    val description: String? = null,

    @SerialName("playerWidth")
    val playerWidth: Int? = null,

    @SerialName("streamWidth")
    val streamWidth: Int? = null,

    @SerialName("playerHeight")
    val playerHeight: Int? = null,

    @SerialName("streamHeight")
    val streamHeight: Int? = null,

    @SerialName("geoCountryAllow")
    val geoCountryAllow: String? = null,

    @SerialName("geoCountryBlock")
    val geoCountryBlock: String? = null,

    @SerialName("audioBitRateKbps")
    val audioBitRateKbps: Int? = null,

    @SerialName("streamStretching")
    val streamStretching: String? = null,

    @SerialName("uniqueStreamName")
    val uniqueStreamName: String? = null,

    @SerialName("videoBitRateKbps")
    val videoBitRateKbps: Int? = null
)