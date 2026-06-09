package ru.grinin.winlineapp.data.models.remote.eventsresponse

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CompetitorDto(
    @SerialName("id")
    val id: Long,

    @SerialName("img")
    val img: ImgDto? = null,

    @SerialName("state")
    val state: Int? = null,

    @SerialName("players")
    val players: List<PlayerDto>? = null,

    @SerialName("image_id")
    val imageId: Int? = null,

    @SerialName("qualifier")
    val qualifier: String,

    @SerialName("country_code")
    val countryCode: String? = null,

    @SerialName("is_duplicate")
    val isDuplicate: Boolean? = null,

    @SerialName("name")
    val name: LocalizedName,

    @SerialName("abbreviation")
    val abbreviation: LocalizedName? = null
)