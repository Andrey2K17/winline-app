package ru.grinin.winlineapp.presentation.state

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

sealed interface EventsUiState {
    object Loading : EventsUiState
    data class Success(val events: Flow<PagingData<EventVO>>) : EventsUiState
    data class Error(val message: String) : EventsUiState
}

data class EventVO(
    val id: Long,
    val homeTeamName: String,
    val awayTeamName: String,
)