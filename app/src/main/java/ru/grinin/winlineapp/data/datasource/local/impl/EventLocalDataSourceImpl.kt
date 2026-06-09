package ru.grinin.winlineapp.data.datasource.local.impl

import androidx.paging.PagingSource
import ru.grinin.winlineapp.data.database.dao.EventDao
import ru.grinin.winlineapp.data.datasource.local.EventLocalDataSource
import ru.grinin.winlineapp.data.models.local.EventEntity

class EventLocalDataSourceImpl(
    private val eventDao: EventDao
) : EventLocalDataSource {

    override suspend fun insertAll(events: List<EventEntity>) {
        eventDao.insertAll(events)
    }

    override suspend fun clearAll() {
        eventDao.clearAll()
    }

    override fun getLiveEventsPagingSource(): PagingSource<Int, EventEntity> {
        return eventDao.getLiveEventsPagingSource()
    }
}