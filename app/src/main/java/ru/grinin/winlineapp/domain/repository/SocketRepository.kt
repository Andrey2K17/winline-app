package ru.grinin.winlineapp.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.grinin.winlineapp.data.models.remote.eventdetails.OddsUpdate

interface SocketRepository {
    fun connect()
    fun disconnect()
    fun observeEventIds(): Flow<Long>
    fun observeOddsUpdates(): Flow<List<OddsUpdate>>
}