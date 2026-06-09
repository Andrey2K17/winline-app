package ru.grinin.winlineapp.domain.repository

import kotlinx.coroutines.flow.Flow

interface SocketRepository {
    fun connect()
    fun disconnect()
    fun observeEventIds(): Flow<Long>
}