package ru.grinin.winlineapp.data.models.remote.eventsrequest

import kotlinx.serialization.Serializable

@Serializable
data class WithQuery(
    val markets: MarketsFilter
)