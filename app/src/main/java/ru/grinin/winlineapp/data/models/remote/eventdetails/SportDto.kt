package ru.grinin.winlineapp.data.models.remote.eventdetails

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.grinin.winlineapp.data.models.remote.eventsresponse.LocalizedName

@Serializable
data class SportDto(
    @SerialName("id")
    val id: Int,

    @SerialName("state")
    val state: Int? = null,

    @SerialName("weight")
    val weight: Int? = null,

    @SerialName("name")
    val name: LocalizedName
)