package ru.grinin.winlineapp.data.socket

object SocketConstants {
    const val EVENT_DISCONNECT = "disconnect"
    const val EVENT_CONNECT_ERROR = "connect_error"

    const val EVENT_ODDS_CHANGE = "sb_odds_change"
    const val EVENT_FIXTURE_CHANGE = "sb_fixture_change"
    const val EVENT_BET_STOP = "sb_bet_stop"

    const val SOCKET_URL = "https://kz.stage.sxl.bet"
    const val SOCKET_PATH = "/ws"
    const val SOCKET_QUERY = "EIO=4"
    const val TRANSPORT_WEBSOCKET = "websocket"

    const val RECONNECTION_ATTEMPTS = 5
    const val RECONNECTION_DELAY_MS = 1000L
    const val RECONNECTION_DELAY_MAX_MS = 5000L

    const val BUFFER_CAPACITY = 64
}