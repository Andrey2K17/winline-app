package ru.grinin.winlineapp.domain.usecase

import ru.grinin.winlineapp.domain.utils.EventDetailsHelper
import ru.grinin.winlineapp.presentation.models.EventDetailsVO
import ru.grinin.winlineapp.presentation.models.OddsChangeDirection

class RemoveBlinkingUseCase(
    private val helper: EventDetailsHelper
) {

    operator fun invoke(
        event: EventDetailsVO,
        marketKey: String,
        outcomeId: Int
    ): EventDetailsVO {
        val result = helper.findMarketAndOutcome(event, marketKey, outcomeId) ?: return event

        val updatedOutcomes = result.market.outcomes.toMutableList().apply {
            set(
                result.outcomeIndex,
                result.outcome.copy(
                    isBlinking = false,
                    changeDirection = OddsChangeDirection.NONE,
                    previousOdds = null
                )
            )
        }

        val updatedMarkets = event.markets.toMutableList().apply {
            set(result.marketIndex, result.market.copy(outcomes = updatedOutcomes))
        }

        return event.copy(markets = updatedMarkets)
    }
}