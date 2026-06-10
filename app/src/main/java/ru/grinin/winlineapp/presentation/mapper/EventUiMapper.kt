package ru.grinin.winlineapp.presentation.mapper

import ru.grinin.winlineapp.data.cache.SportsCache
import ru.grinin.winlineapp.data.models.local.EventEntity
import ru.grinin.winlineapp.presentation.state.EventVO

class EventUiMapper(
    private val sportsCache: SportsCache,
) {
    fun toUiModel(entity: EventEntity): EventVO {
        val fullPath = sportsCache.getFullPath(
            entity.sportId,
            entity.categoryId,
            entity.tournamentId
        )

        return EventVO(
            id = entity.id,
            homeTeamName = entity.homeTeamName,
            awayTeamName = entity.awayTeamName,
            fullPath = fullPath
        )
    }
}