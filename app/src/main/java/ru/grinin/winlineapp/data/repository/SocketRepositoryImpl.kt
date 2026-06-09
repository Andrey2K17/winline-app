package ru.grinin.winlineapp.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.intOrNull
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import ru.grinin.winlineapp.data.socket.SocketService
import ru.grinin.winlineapp.domain.repository.SocketRepository

class SocketRepositoryImpl(
    private val socketService: SocketService,
    private val json: Json,
) : SocketRepository {
    override fun connect() {
        socketService.connect()
    }

    override fun disconnect() {
        socketService.disconnect()
    }

    override fun observeEventIds(): Flow<Long> =
        socketService.messages
            .filter { message ->
                message.type == SocketService.SocketEventType.ODDS_CHANGE ||
                        message.type == SocketService.SocketEventType.FIXTURE_CHANGE ||
                        message.type == SocketService.SocketEventType.BET_STOP
            }
            .map { message ->
                extractEventId(message.data.orEmpty())
            }
            .filter { it != 0L }

    private fun extractEventId(jsonString: String): Long {
        return try {
            val jsonElement = json.parseToJsonElement(jsonString)
            val jsonObject = jsonElement.jsonObject

            jsonObject["id"]?.jsonPrimitive?.intOrNull?.toLong()
                ?: jsonObject["eventId"]?.jsonPrimitive?.intOrNull?.toLong()
                ?: 0L
        } catch (e: Exception) {
            val regex = Regex("\"id\":(\\d+)")
            regex.find(jsonString)?.groupValues?.get(1)?.toLongOrNull() ?: 0L
        }
    }
}