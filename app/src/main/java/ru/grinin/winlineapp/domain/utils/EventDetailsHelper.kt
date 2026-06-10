package ru.grinin.winlineapp.domain.utils

import ru.grinin.winlineapp.presentation.models.EventDetailsVO
import ru.grinin.winlineapp.presentation.models.MarketDetailsVO
import ru.grinin.winlineapp.presentation.models.OutcomeDetailsVO

class EventDetailsHelper {

    fun findMarketIndex(markets: List<MarketDetailsVO>, marketKey: String): Int {
        return markets.indexOfFirst { it.uniqueKey == marketKey }
    }

    fun findOutcomeIndex(outcomes: List<OutcomeDetailsVO>, outcomeId: Int): Int {
        return outcomes.indexOfFirst { it.id == outcomeId }
    }

    fun findMarketAndOutcome(
        event: EventDetailsVO,
        marketKey: String,
        outcomeId: Int
    ): MarketAndOutcomeResult? {
        val marketIndex = findMarketIndex(event.markets, marketKey)
        if (marketIndex == -1) return null

        val market = event.markets[marketIndex]
        val outcomeIndex = findOutcomeIndex(market.outcomes, outcomeId)
        if (outcomeIndex == -1) return null

        val outcome = market.outcomes[outcomeIndex]

        return MarketAndOutcomeResult(
            marketIndex = marketIndex,
            market = market,
            outcomeIndex = outcomeIndex,
            outcome = outcome
        )
    }

    data class MarketAndOutcomeResult(
        val marketIndex: Int,
        val market: MarketDetailsVO,
        val outcomeIndex: Int,
        val outcome: OutcomeDetailsVO
    )
}