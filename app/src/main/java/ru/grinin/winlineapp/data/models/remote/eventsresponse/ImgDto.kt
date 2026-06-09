package ru.grinin.winlineapp.data.models.remote.eventsresponse

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ImgDto(
    @SerialName("big")
    val big: String? = null,

    @SerialName("small")
    val small: String? = null,

    @SerialName("medium")
    val medium: String? = null
)