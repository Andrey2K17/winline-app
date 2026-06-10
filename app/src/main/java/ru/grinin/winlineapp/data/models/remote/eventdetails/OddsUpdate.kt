package ru.grinin.winlineapp.data.models.remote.eventdetails

data class OddsUpdate(
    val eventId: Long,
    val marketId: Int,
    val marketSpecifiers: String,
    val outcomeId: Int,
    val newOdds: Double
) {
    val marketKey: String get() = "$marketId|$marketSpecifiers"
}