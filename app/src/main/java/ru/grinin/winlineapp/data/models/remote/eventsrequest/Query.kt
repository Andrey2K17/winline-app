package ru.grinin.winlineapp.data.models.remote.eventsrequest

import kotlinx.serialization.Serializable

@Serializable
data class Query(
    val scheduled: Scheduled,
    val type: String = "match"
)