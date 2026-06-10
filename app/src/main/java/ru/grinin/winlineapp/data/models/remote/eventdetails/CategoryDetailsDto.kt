package ru.grinin.winlineapp.data.models.remote.eventdetails

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.grinin.winlineapp.data.models.remote.eventsresponse.LocalizedName

@Serializable
data class CategoryDetailsDto(
    @SerialName("id")
    val id: Int,

    @SerialName("state")
    val state: Int? = null,

    @SerialName("sport_id")
    val sportId: Int,

    @SerialName("name")
    val name: LocalizedName
)