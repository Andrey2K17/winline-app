package ru.grinin.winlineapp.data.models.remote.eventdetails

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MarketDetailsDto(
    @SerialName("id")
    val id: Int,

    @SerialName("meta")
    val meta: MarketMetaDetailsDto? = null,

    @SerialName("state")
    val state: Int? = null,

    @SerialName("status")
    val status: Int? = null,

    @SerialName("weight")
    val weight: Int? = null,

    @SerialName("provider")
    val provider: Int? = null,

    @SerialName("favourite")
    val favourite: Boolean? = null,

    @SerialName("timestamp")
    val timestamp: Long? = null,

    @SerialName("specifiers")
    val specifiers: String? = null,

    @SerialName("outcomes")
    val outcomes: List<OutcomeDetailsDto>?
)