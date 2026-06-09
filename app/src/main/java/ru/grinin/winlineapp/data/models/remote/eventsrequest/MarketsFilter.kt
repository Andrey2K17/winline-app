package ru.grinin.winlineapp.data.models.remote.eventsrequest

import kotlinx.serialization.Serializable

@Serializable
data class MarketsFilter(
    val id: IdFilter
)