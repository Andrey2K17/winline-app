package ru.grinin.winlineapp.data.models.remote.eventdetails

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PeriodScoreDto(
    @SerialName("number")
    val number: Int? = null,

    @SerialName("away_score")
    val awayScore: Int? = null,

    @SerialName("home_score")
    val homeScore: Int? = null,

    @SerialName("match_status_code")
    val matchStatusCode: Int? = null
)