package ru.grinin.winlineapp.data.models.remote.eventdetails

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.grinin.winlineapp.data.models.remote.eventsresponse.LocalizedName

@Serializable
data class CompetitorDetailsDto(
    @SerialName("id")
    val id: Long,

    @SerialName("state")
    val state: Int? = null,

    @SerialName("image_id")
    val imageId: Int? = null,

    @SerialName("qualifier")
    val qualifier: String,

    @SerialName("is_duplicate")
    val isDuplicate: Boolean? = null,

    @SerialName("name")
    val name: LocalizedName,

    @SerialName("abbreviation")
    val abbreviation: LocalizedName? = null,

    @SerialName("country_code")
    val countryCode: String? = null
)