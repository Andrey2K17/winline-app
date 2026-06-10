package ru.grinin.winlineapp.presentation.state

import ru.grinin.winlineapp.presentation.models.EventDetailsVO

sealed interface EventDetailsUiState {
    object Loading : EventDetailsUiState
    data class Error(val message: String) : EventDetailsUiState
    data class Success(val event: EventDetailsVO) : EventDetailsUiState
}