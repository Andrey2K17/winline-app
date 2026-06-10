package ru.grinin.winlineapp.data.models.remote.eventdetails

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class IGameDto(
    @SerialName("eventId")
    val eventId: String? = null,

    @SerialName("streams")
    val streams: List<StreamDto>? = null
)