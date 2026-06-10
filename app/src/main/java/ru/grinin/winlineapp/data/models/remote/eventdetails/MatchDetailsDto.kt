package ru.grinin.winlineapp.data.models.remote.eventdetails

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MatchDetailsDto(
    @SerialName("stats")
    val stats: MatchStatsDto? = null,

    @SerialName("status")
    val status: Int? = null,

    @SerialName("timestamp")
    val timestamp: Long? = null,
)