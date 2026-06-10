package ru.grinin.winlineapp.data.models.remote.eventdetails

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.grinin.winlineapp.data.models.remote.eventsresponse.LocalizedName

@Serializable
data class TournamentDetailsDto(
    @SerialName("id")
    val id: Int,

    @SerialName("state")
    val state: Int? = null,

    @SerialName("category_id")
    val categoryId: Int,

    @SerialName("previousName")
    val previousName: LocalizedName? = null,

    @SerialName("name")
    val name: LocalizedName
)