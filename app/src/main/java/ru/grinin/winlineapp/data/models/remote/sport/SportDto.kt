package ru.grinin.winlineapp.data.models.remote.sport

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


typealias SportResponseDto = List<SportDto>

@Serializable
data class SportDto(
    @SerialName("id")
    val id: Int,

    @SerialName("name")
    val name: String,

    @SerialName("state")
    val state: Int? = null,

    @SerialName("weight")
    val weight: Int? = null,

    @SerialName("previousName")
    val previousName: PreviousName? = null,

    @SerialName("categories")
    val categories: List<CategoryDto>? = null
)
@Serializable
data class PreviousName(
    @SerialName("en")
    val en: String? = null,

    @SerialName("ru")
    val ru: String? = null
)
@Serializable
data class CategoryDto(
    @SerialName("id")
    val id: Int,

    @SerialName("name")
    val name: String,

    @SerialName("state")
    val state: Int? = null,

    @SerialName("sport_id")
    val sportId: Int,

    @SerialName("country_code")
    val countryCode: String? = null,

    @SerialName("tournaments")
    val tournaments: List<TournamentDto>? = null
)
@Serializable
data class TournamentDto(
    @SerialName("id")
    val id: Int,

    @SerialName("name")
    val name: String,

    @SerialName("state")
    val state: Int? = null,

    @SerialName("category_id")
    val categoryId: Int,

    @SerialName("sportEventsCount")
    val sportEventsCount: Int? = null,

    @SerialName("meanWeight")
    val meanWeight: Double? = null,

    @SerialName("image")
    val image: String? = null,

    @SerialName("scheduled")
    val scheduled: String? = null
)