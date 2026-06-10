package ru.grinin.winlineapp.data.models.remote.eventdetails

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MarketMetaDetailsDto(
    @SerialName("pauseApp")
    val pauseApp: Int? = null,

    @SerialName("pauseSait")
    val pauseSait: Int? = null,

    @SerialName("externalId")
    val externalId: Long? = null
)