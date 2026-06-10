package ru.grinin.winlineapp.data.models.remote.eventdetails

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OutcomeDetailsDto(
    @SerialName("id")
    val id: Int,

    @SerialName("meta")
    val meta: OutcomeMetaDetailsDto? = null,

    @SerialName("odds")
    val odds: Double,

    @SerialName("type")
    val type: String? = null,

    @SerialName("state")
    val state: Int? = null,

    @SerialName("active")
    val active: Boolean? = null
)