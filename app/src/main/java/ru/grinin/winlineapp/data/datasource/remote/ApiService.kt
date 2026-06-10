package ru.grinin.winlineapp.data.datasource.remote

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import ru.grinin.winlineapp.data.models.remote.eventsrequest.EventsRequest
import ru.grinin.winlineapp.data.models.remote.eventsresponse.EventsResponseDto
import ru.grinin.winlineapp.data.models.remote.sport.SportResponseDto

interface ApiService {
    @POST("sb/api/actual")
    suspend fun getEvents(
        @Body request: EventsRequest
    ): EventsResponseDto

    @GET("sb/api/actual/tree")
    suspend fun getSportsTree(
        @Query("lang") lang: String = "ru",
        @Query("fallbackLang") fallbackLang: String = "ru"
    ): SportResponseDto
}