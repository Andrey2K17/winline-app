package ru.grinin.winlineapp.data.models.remote.eventsresponse

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EventDto(
    @SerialName("id")
    val id: Long,

    @SerialName("status")
    val status: Int,

    @SerialName("scheduled")
    val scheduled: Long,

    @SerialName("competitors")
    val competitors: List<CompetitorDto>?
)





