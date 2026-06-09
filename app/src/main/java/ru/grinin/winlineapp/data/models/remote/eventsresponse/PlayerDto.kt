package ru.grinin.winlineapp.data.models.remote.eventsresponse

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PlayerDto(
    @SerialName("id")
    val id: Long,

    @SerialName("state")
    val state: Int? = null,

    @SerialName("name")
    val name: LocalizedName
)