package ru.grinin.winlineapp.presentation.state

sealed interface EventsUiState {
    object Loading : EventsUiState
    object Success: EventsUiState
    data class Error(val message: String) : EventsUiState
}