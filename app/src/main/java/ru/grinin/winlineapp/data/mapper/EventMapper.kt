package ru.grinin.winlineapp.data.mapper

import ru.grinin.winlineapp.data.models.local.EventEntity
import ru.grinin.winlineapp.data.models.remote.eventsresponse.EventDto

private const val UNKNOWN = "Unknown"
class DataToEntityMapper {
    fun toEntity(dto: EventDto): EventEntity? {
        val home = dto.competitors?.find { it.qualifier == "home" } ?: return null
        val away = dto.competitors.find { it.qualifier == "away" } ?: return null

        return EventEntity(
            id = dto.id,
            scheduled = dto.scheduled,
            homeTeamName = home.name.ru ?: home.name.en ?: UNKNOWN,
            awayTeamName = away.name.ru ?: away.name.en ?: UNKNOWN,
            sportId = dto.sportId,
            categoryId = dto.categoryId,
            tournamentId = dto.tournamentId,
        )
    }
}