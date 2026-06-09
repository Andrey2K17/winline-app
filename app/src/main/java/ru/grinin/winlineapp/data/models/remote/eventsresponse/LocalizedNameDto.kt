package ru.grinin.winlineapp.data.models.remote.eventsresponse

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LocalizedName(
    @SerialName("en")
    val en: String? = null,

    @SerialName("ru")
    val ru: String? = null,

    @SerialName("ar")
    val ar: String? = null,
)