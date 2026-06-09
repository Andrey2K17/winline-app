package ru.grinin.winlineapp.data.models.remote.eventsrequest

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Scheduled(
    @SerialName($$"$lte")
    val lte: Long
)
