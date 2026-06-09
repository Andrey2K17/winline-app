package ru.grinin.winlineapp.data.datasource.remote

import retrofit2.http.Body
import retrofit2.http.POST
import ru.grinin.winlineapp.data.models.remote.eventsrequest.EventsRequest
import ru.grinin.winlineapp.data.models.remote.eventsresponse.EventsResponseDto

interface ApiService {
    @POST("sb/api/actual")
    suspend fun getEvents(
        @Body request: EventsRequest
    ): EventsResponseDto
}