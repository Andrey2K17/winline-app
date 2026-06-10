package ru.grinin.winlineapp.presentation.models

data class EventDetailsVO(
    val id: Long,
    val markets: List<MarketDetailsVO>
)

data class MarketDetailsVO(
    val id: Int,
    val specifiers: String,
    val uniqueKey: String = "$id|$specifiers",
    val state: Int,
    val status: Int,
    val weight: Int,
    val provider: Int,
    val favourite: Boolean,
    val timestamp: Long,
    val outcomes: List<OutcomeDetailsVO>
)

enum class OddsChangeDirection {
    UP,
    DOWN,
    NONE
}

data class OutcomeDetailsVO(
    val id: Int,
    val meta: OutcomeMetaDetailsVO,
    val odds: Double,
    val previousOdds: Double? = null,
    val type: String,
    val state: Int,
    val active: Boolean,
    val isBlinking: Boolean = false,
    val changeDirection: OddsChangeDirection = OddsChangeDirection.NONE,
)
data class OutcomeMetaDetailsVO(
    val ratioIndex: Int
)