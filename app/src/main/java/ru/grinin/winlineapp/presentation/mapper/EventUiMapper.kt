package ru.grinin.winlineapp.presentation.mapper

import ru.grinin.winlineapp.data.models.local.EventEntity
import ru.grinin.winlineapp.presentation.state.EventVO

class EventUiMapper {
    fun toUiModel(entity: EventEntity) =
        EventVO(
            id = entity.id,
            homeTeamName = entity.homeTeamName,
            awayTeamName = entity.awayTeamName,
        )
}