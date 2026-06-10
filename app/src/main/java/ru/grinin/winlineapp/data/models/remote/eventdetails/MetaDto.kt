package ru.grinin.winlineapp.data.models.remote.eventdetails

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MetaDto(
    @SerialName("ownId")
    val ownId: Long? = null,

    @SerialName("rule1")
    val rule1: Boolean? = null,

    @SerialName("rule2")
    val rule2: Boolean? = null,

    @SerialName("rule3")
    val rule3: Boolean? = null,

    @SerialName("noExpress")
    val noExpress: Int? = null,

    @SerialName("tradingStatus")
    val tradingStatus: Int? = null
)