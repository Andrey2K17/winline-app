package ru.grinin.winlineapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.grinin.winlineapp.data.datasource.local.EventLocalDataSource
import ru.grinin.winlineapp.data.datasource.remote.EventRemoteDataSource
import ru.grinin.winlineapp.data.mapper.DataToEntityMapper
import ru.grinin.winlineapp.data.models.local.EventEntity
import ru.grinin.winlineapp.domain.repository.EventRepository

class EventRepositoryImpl(
    private val remoteDataSource: EventRemoteDataSource,
    private val localDataSource: EventLocalDataSource,
    private val dataToEntityMapper: DataToEntityMapper,
) : EventRepository {

    override suspend fun refreshEvents() {
        val allEvents = remoteDataSource.getEvents()

        val liveEvents = allEvents
            .filter { it.status == 1 }
            .mapNotNull { dataToEntityMapper.toEntity(it) }

        localDataSource.clearAll()
        if (liveEvents.isNotEmpty()) {
            localDataSource.insertAll(liveEvents)
        }
    }

    override fun getLiveEvents(): Flow<PagingData<EventEntity>> {
        return Pager(
            config = PagingConfig(pageSize = 20, prefetchDistance = 5),
            pagingSourceFactory = { localDataSource.getLiveEventsPagingSource() }
        ).flow.map { pagingData ->
            pagingData.map { it }
        }
    }
}