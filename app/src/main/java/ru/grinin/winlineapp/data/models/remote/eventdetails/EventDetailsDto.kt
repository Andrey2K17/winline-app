package ru.grinin.winlineapp.data.models.remote.eventdetails

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EventDetailsDto(
    @SerialName("id")
    val id: Long,

    @SerialName("meta")
    val meta: MetaDto? = null,

    @SerialName("type")
    val type: String? = null,

    @SerialName("igame")
    val igame: IGameDto? = null,

    @SerialName("state")
    val state: Int? = null,

    @SerialName("video")
    val video: String? = null,

    @SerialName("status")
    val status: Int,

    @SerialName("weight")
    val weight: Int? = null,

    @SerialName("product")
    val product: String? = null,

    @SerialName("se_type")
    val seType: String? = null,

    @SerialName("liveodds")
    val liveodds: String? = null,

    @SerialName("provider")
    val provider: Int? = null,

    @SerialName("sport_id")
    val sportId: Int,

    @SerialName("scheduled")
    val scheduled: Long,

    @SerialName("timestamp")
    val timestamp: Long? = null,

    @SerialName("category_id")
    val categoryId: Int,

    @SerialName("scheduled_end")
    val scheduledEnd: Long? = null,

    @SerialName("tournament_id")
    val tournamentId: Int,

    @SerialName("start_time_tbd")
    val startTimeTbd: Boolean? = null,

    @SerialName("trading_status")
    val tradingStatus: Int? = null,

    @SerialName("activeMarketsCount")
    val activeMarketsCount: Int? = null,

    @SerialName("markets")
    val markets: List<MarketDetailsDto>? = null,

    @SerialName("sport")
    val sport: SportDto? = null,

    @SerialName("category")
    val category: CategoryDetailsDto? = null,

    @SerialName("tournament")
    val tournament: TournamentDetailsDto? = null,

    @SerialName("competitors")
    val competitors: List<CompetitorDetailsDto>?
)