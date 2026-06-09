package ru.grinin.winlineapp.data.datasource.remote

import ru.grinin.winlineapp.data.models.remote.eventsresponse.EventsResponseDto

interface EventRemoteDataSource {
    suspend fun getEvents(): EventsResponseDto
}