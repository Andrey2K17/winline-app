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

    @SerialName("sport_id")
    val sportId: Int,

    @SerialName("category_id")
    val categoryId: Int,

    @SerialName("tournament_id")
    val tournamentId: Int,

    @SerialName("competitors")
    val competitors: List<CompetitorDto>?
)





