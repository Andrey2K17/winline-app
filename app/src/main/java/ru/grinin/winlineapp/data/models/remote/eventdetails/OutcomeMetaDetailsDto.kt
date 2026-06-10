package ru.grinin.winlineapp.data.models.remote.eventdetails

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OutcomeMetaDetailsDto(
    @SerialName("ratioIndex")
    val ratioIndex: Int? = null
)