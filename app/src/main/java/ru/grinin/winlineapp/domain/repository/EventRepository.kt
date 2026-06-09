package ru.grinin.winlineapp.domain.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.grinin.winlineapp.data.models.local.EventEntity

interface EventRepository {
    suspend fun refreshEvents()
    fun getLiveEvents(): Flow<PagingData<EventEntity>>
}