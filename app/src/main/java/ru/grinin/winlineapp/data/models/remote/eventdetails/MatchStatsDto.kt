package ru.grinin.winlineapp.data.models.remote.eventdetails

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MatchStatsDto(
    @SerialName("away_score")
    val awayScore: Int? = null,

    @SerialName("home_score")
    val homeScore: Int? = null,

    @SerialName("match_status")
    val matchStatus: Int? = null,

    @SerialName("period_scores")
    val periodScores: List<PeriodScoreDto>? = null,

    @SerialName("away_gamescore")
    val awayGamescore: Int? = null,

    @SerialName("current_server")
    val currentServer: Int? = null,

    @SerialName("home_gamescore")
    val homeGamescore: Int? = null
)