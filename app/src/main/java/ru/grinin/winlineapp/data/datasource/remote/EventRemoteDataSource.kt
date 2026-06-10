package ru.grinin.winlineapp.data.datasource.remote

import ru.grinin.winlineapp.data.models.remote.eventsresponse.EventsResponseDto
import ru.grinin.winlineapp.data.models.remote.sport.SportResponseDto

interface EventRemoteDataSource {
    suspend fun getEvents(): EventsResponseDto

    suspend fun getSports(): SportResponseDto
}