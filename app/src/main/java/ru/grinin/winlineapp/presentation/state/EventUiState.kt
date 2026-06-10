package ru.grinin.winlineapp.presentation.state

sealed interface EventsUiState {
    object Loading : EventsUiState
    object Success: EventsUiState
    data class Error(val message: String) : EventsUiState
}

data class EventVO(
    val id: Long,
    val homeTeamName: String,
    val awayTeamName: String,
    val fullPath: String,
)