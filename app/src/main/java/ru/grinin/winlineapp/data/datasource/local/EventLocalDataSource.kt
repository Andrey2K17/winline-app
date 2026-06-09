package ru.grinin.winlineapp.data.datasource.local

import androidx.paging.PagingSource
import ru.grinin.winlineapp.data.models.local.EventEntity

interface EventLocalDataSource {
    suspend fun insertAll(events: List<EventEntity>)
    suspend fun clearAll()
    fun getLiveEventsPagingSource(): PagingSource<Int, EventEntity>
}