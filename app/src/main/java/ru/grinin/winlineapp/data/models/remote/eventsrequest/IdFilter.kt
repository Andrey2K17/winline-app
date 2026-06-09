package ru.grinin.winlineapp.data.models.remote.eventsrequest

import kotlinx.serialization.Serializable

@Serializable
data class IdFilter(
    val `in`: List<Int>
)
