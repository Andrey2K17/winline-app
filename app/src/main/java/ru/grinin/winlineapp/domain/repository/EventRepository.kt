package ru.grinin.winlineapp.domain.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.grinin.winlineapp.data.models.local.EventEntity
import ru.grinin.winlineapp.data.models.remote.eventdetails.EventDetailsDto

interface EventRepository {
    suspend fun refreshEvents()
    fun getLiveEvents(): Flow<PagingData<EventEntity>>

    suspend fun getEventDetails(eventId: Long): EventDetailsDto?
}