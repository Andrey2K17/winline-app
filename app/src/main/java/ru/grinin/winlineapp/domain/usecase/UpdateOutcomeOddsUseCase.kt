package ru.grinin.winlineapp.domain.usecase

import ru.grinin.winlineapp.domain.utils.EventDetailsHelper
import ru.grinin.winlineapp.presentation.models.EventDetailsVO
import ru.grinin.winlineapp.presentation.models.OddsChangeDirection

class UpdateOutcomeOddsUseCase(
    private val helper: EventDetailsHelper
) {

    operator fun invoke(
        event: EventDetailsVO,
        marketKey: String,
        outcomeId: Int,
        newOdds: Double
    ): EventDetailsVO {
        val result = helper.findMarketAndOutcome(event, marketKey, outcomeId) ?: return event

        val oldOdds = result.outcome.odds
        val changeDirection = when {
            newOdds > oldOdds -> OddsChangeDirection.UP
            newOdds < oldOdds -> OddsChangeDirection.DOWN
            else -> OddsChangeDirection.NONE
        }

        val updatedOutcomes = result.market.outcomes.toMutableList().apply {
            set(
                result.outcomeIndex,
                result.outcome.copy(
                    odds = newOdds,
                    previousOdds = oldOdds,
                    changeDirection = changeDirection,
                    isBlinking = true
                )
            )
        }

        val updatedMarkets = event.markets.toMutableList().apply {
            set(result.marketIndex, result.market.copy(outcomes = updatedOutcomes))
        }

        return event.copy(markets = updatedMarkets)
    }
}