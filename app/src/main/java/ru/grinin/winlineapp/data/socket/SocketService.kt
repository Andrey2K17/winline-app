package ru.grinin.winlineapp.data.socket

import io.socket.client.IO
import io.socket.client.Socket
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch


class SocketService {

    enum class SocketEventType {
        ODDS_CHANGE,
        FIXTURE_CHANGE,
        BET_STOP,
        CONNECTED,
        DISCONNECTED,
        ERROR
    }

    data class SocketMessage(
        val type: SocketEventType,
        val data: String? = null,
        val error: Throwable? = null
    )

    private var socket: Socket? = null
    private var serviceScope: CoroutineScope? = null

    private val _messages = MutableSharedFlow<SocketMessage>(
        extraBufferCapacity = SocketConstants.BUFFER_CAPACITY
    )
    val messages: SharedFlow<SocketMessage> = _messages.asSharedFlow()

    fun connect() {
        if (socket?.connected() == true) {
            return
        }
        serviceScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

        try {

            val options = IO.Options().apply {
                path = SocketConstants.SOCKET_PATH
                reconnection = true
                reconnectionAttempts = SocketConstants.RECONNECTION_ATTEMPTS
                reconnectionDelay = SocketConstants.RECONNECTION_DELAY_MS
                reconnectionDelayMax = SocketConstants.RECONNECTION_DELAY_MAX_MS
                transports = arrayOf(SocketConstants.TRANSPORT_WEBSOCKET)
                secure = true
                query = SocketConstants.SOCKET_QUERY
            }

            socket = IO.socket(SocketConstants.SOCKET_URL, options)

            setupListeners()
            socket?.connect()

        } catch (e: Exception) {
            serviceScope?.launch {
                _messages.emit(SocketMessage(SocketEventType.ERROR, error = e))
            }
        }
    }

    private fun setupListeners() {

        socket?.apply {
            on(SocketConstants.EVENT_DISCONNECT) {
                serviceScope?.launch {
                    _messages.emit(SocketMessage(SocketEventType.CONNECTED))
                }
            }

            on(SocketConstants.EVENT_DISCONNECT) {
                serviceScope?.launch {
                    _messages.emit(SocketMessage(SocketEventType.DISCONNECTED))
                }
            }

            on(SocketConstants.EVENT_CONNECT_ERROR) { args ->
                val error = args.firstOrNull() as? Exception ?: Exception("Unknown error")
                serviceScope?.launch {
                    _messages.emit(SocketMessage(SocketEventType.ERROR, error = error))
                }
            }

        }

        handleEvent(SocketConstants.EVENT_ODDS_CHANGE, SocketEventType.ODDS_CHANGE)
        handleEvent(SocketConstants.EVENT_FIXTURE_CHANGE, SocketEventType.FIXTURE_CHANGE)
        handleEvent(SocketConstants.EVENT_BET_STOP, SocketEventType.BET_STOP)
    }

    private fun handleEvent(eventName: String, eventType: SocketEventType) {
        socket?.on(eventName) { args ->
            val data = args.firstOrNull()?.toString()

            data?.let {
                serviceScope?.launch {
                    _messages.emit(SocketMessage(eventType, it))
                }
            }
        }
    }

    fun disconnect() {
        serviceScope?.cancel()
        serviceScope = null

        socket?.let {
            if (it.connected()) {
                it.disconnect()
            }
            it.off()
        }
        socket = null
    }
}