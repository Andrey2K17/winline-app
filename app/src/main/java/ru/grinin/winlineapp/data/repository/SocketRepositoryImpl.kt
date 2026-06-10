package ru.grinin.winlineapp.data.repository

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.doubleOrNull
import kotlinx.serialization.json.intOrNull
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import ru.grinin.winlineapp.data.models.remote.eventdetails.OddsUpdate
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

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun observeOddsUpdates(): Flow<List<OddsUpdate>> =
        socketService.messages
            .filter { message -> message.type == SocketService.SocketEventType.ODDS_CHANGE }
            .map { message ->
                extractOddsUpdate(message.data.orEmpty())
            }
            .filterNotNull()

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

    private fun extractOddsUpdate(jsonString: String): List<OddsUpdate> {
        val updates = mutableListOf<OddsUpdate>()
         try {
            val jsonElement = json.parseToJsonElement(jsonString)
            val jsonObject = jsonElement.jsonObject

            val eventId = jsonObject["id"]?.jsonPrimitive?.intOrNull?.toLong() ?: return emptyList()

            val markets = jsonObject["markets"]?.jsonArray ?: return emptyList()

            markets.forEach { marketElement ->
                val marketObj = marketElement.jsonObject
                val marketId = marketObj["id"]?.jsonPrimitive?.intOrNull ?: return@forEach
                val specifiers = marketObj["specifiers"]?.jsonPrimitive?.content ?: ""
                val outcomes = marketObj["outcomes"]?.jsonArray ?: return@forEach

                outcomes.forEach { outcomeElement ->
                    val outcomeObj = outcomeElement.jsonObject
                    val outcomeId = outcomeObj["id"]?.jsonPrimitive?.intOrNull ?: return@forEach
                    val odds = outcomeObj["odds"]?.jsonPrimitive?.doubleOrNull ?: return@forEach

                    updates.add(
                        OddsUpdate(
                            eventId = eventId,
                            marketId = marketId,
                            marketSpecifiers = specifiers,
                            outcomeId = outcomeId,
                            newOdds = odds
                        )
                    )
                }
            }

        } catch (e: Exception) {
            extractOddsUpdateByRegex(jsonString)
        }

        return updates
    }

    private fun extractOddsUpdateByRegex(jsonString: String): OddsUpdate? {
        val eventIdRegex = Regex("\"eventId\":(\\d+)|\\\"id\\\":(\\d+)")
        val marketIdRegex = Regex("\"marketId\":(\\d+)")
        val outcomeIdRegex = Regex("\"outcomeId\":(\\d+)")
        val oddsRegex = Regex("\"odds\":([\\d.]+)")
        val specifiersRegex = Regex("\"specifiers\":\"([^\"]+)\"")

        val eventId = eventIdRegex.find(jsonString)?.groupValues?.get(1)?.toLongOrNull()
            ?: eventIdRegex.find(jsonString)?.groupValues?.get(2)?.toLongOrNull()
        val marketId = marketIdRegex.find(jsonString)?.groupValues?.get(1)?.toIntOrNull()
        val outcomeId = outcomeIdRegex.find(jsonString)?.groupValues?.get(1)?.toIntOrNull()
        val newOdds = oddsRegex.find(jsonString)?.groupValues?.get(1)?.toDoubleOrNull()
        val marketSpecifiers = specifiersRegex.find(jsonString)?.groupValues?.get(1) ?: ""

        if (eventId == null || marketId == null || outcomeId == null || newOdds == null) {
            return null
        }

        return OddsUpdate(
            eventId = eventId,
            marketId = marketId,
            marketSpecifiers = marketSpecifiers,
            outcomeId = outcomeId,
            newOdds = newOdds
        )
    }
}