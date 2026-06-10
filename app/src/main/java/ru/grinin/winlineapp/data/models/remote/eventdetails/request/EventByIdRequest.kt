package ru.grinin.winlineapp.data.models.remote.eventdetails.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EventByIdRequest(
    val fallbackLang: String = "en",
    val lang: String = "ru",
    val query: EventIdQuery,
    @SerialName("with")
    val with: List<String>,
    val withQuery: WithQueryDetails
)

@Serializable
data class EventIdQuery(
    val id: Long
)

@Serializable
data class WithQueryDetails(
    val markets: MarketsFilterDetails
)

@Serializable
data class MarketsFilterDetails(
    val id: IdFilterDetails
)

@Serializable
data class IdFilterDetails(
    @SerialName("\$in")
    val `in`: List<Int>
)