package ru.grinin.winlineapp.presentation.mapper

import ru.grinin.winlineapp.data.models.remote.eventdetails.EventDetailsDto
import ru.grinin.winlineapp.data.models.remote.eventdetails.MarketDetailsDto
import ru.grinin.winlineapp.data.models.remote.eventdetails.OutcomeDetailsDto
import ru.grinin.winlineapp.data.models.remote.eventdetails.OutcomeMetaDetailsDto
import ru.grinin.winlineapp.presentation.models.EventDetailsVO
import ru.grinin.winlineapp.presentation.models.MarketDetailsVO
import ru.grinin.winlineapp.presentation.models.OutcomeDetailsVO
import ru.grinin.winlineapp.presentation.models.OutcomeMetaDetailsVO
import ru.grinin.winlineapp.utils.errorMapper.EmptyEventDetailsException


class EventDetailsUiMapper {

    fun toUiModel(dto: EventDetailsDto?): EventDetailsVO {
        if (dto == null) throw EmptyEventDetailsException()
        return EventDetailsVO(
            id = dto.id,
            markets = dto.markets?.map { toMarketUiModel(it) } ?: emptyList()
        )
    }

    private fun toMarketUiModel(dto: MarketDetailsDto): MarketDetailsVO {
        return MarketDetailsVO(
            id = dto.id,
            state = dto.state ?: 0,
            status = dto.status ?: 0,
            weight = dto.weight ?: 0,
            provider = dto.provider ?: 0,
            favourite = dto.favourite ?: false,
            timestamp = dto.timestamp ?: 0L,
            specifiers = dto.specifiers ?: "",
            outcomes = dto.outcomes?.map { toOutcomeUiModel(it) } ?: emptyList()
        )
    }

    private fun toOutcomeUiModel(dto: OutcomeDetailsDto): OutcomeDetailsVO {
        return OutcomeDetailsVO(
            id = dto.id,
            meta = toOutcomeMetaUiModel(dto.meta),
            odds = dto.odds,
            type = dto.type ?: "",
            state = dto.state ?: 0,
            active = dto.active ?: false
        )
    }

    private fun toOutcomeMetaUiModel(dto: OutcomeMetaDetailsDto?): OutcomeMetaDetailsVO {
        return OutcomeMetaDetailsVO(
            ratioIndex = dto?.ratioIndex ?: 0
        )
    }
}