package ru.grinin.winlineapp.data.models.remote.eventsrequest

import kotlinx.serialization.Serializable

@Serializable
data class EventsRequest(
    val fallbackLang: String = "en",
    val lang: String = "ru",
    val query: Query,
    val raw: Boolean = false,
    val with: List<String>,
    val withQuery: WithQuery
)
