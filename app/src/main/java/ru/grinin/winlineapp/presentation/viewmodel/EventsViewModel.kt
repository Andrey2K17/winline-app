package ru.grinin.winlineapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.grinin.winlineapp.domain.repository.EventRepository
import ru.grinin.winlineapp.domain.repository.SocketRepository
import ru.grinin.winlineapp.presentation.mapper.EventUiMapper
import ru.grinin.winlineapp.presentation.models.EventVO
import ru.grinin.winlineapp.presentation.state.EventsUiState
import ru.grinin.winlineapp.utils.errorMapper.ErrorMapper
import kotlin.coroutines.cancellation.CancellationException

class EventsViewModel(
    private val eventRepository: EventRepository,
    private val socketRepository: SocketRepository,
    private val eventUiMapper: EventUiMapper,
    private val errorMapper: ErrorMapper,
) : ViewModel() {

    private val _uiState = MutableStateFlow<EventsUiState>(EventsUiState.Loading)
    val uiState: StateFlow<EventsUiState> = _uiState.asStateFlow()

    private val _blinkingEventIds = MutableStateFlow<Set<Long>>(emptySet())

    val pagingItems: Flow<PagingData<EventVO>> =
        eventRepository.getLiveEvents()
            .cachedIn(viewModelScope)
            .combine(_blinkingEventIds) { pagingData, blinkingIds ->
                pagingData.map { entity ->
                    eventUiMapper.toUiModel(entity, blinkingIds.contains(entity.id))
                }
            }
            .cachedIn(viewModelScope)

    init {
        loadEvents()
        initSocket()
    }

    private fun loadEvents() {
        viewModelScope.launch {
            _uiState.value = EventsUiState.Loading
            try {
                eventRepository.refreshEvents()
                _uiState.value = EventsUiState.Success
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _uiState.value = EventsUiState.Error(
                    errorMapper.getMessage(e)
                )
            }
        }
    }

    private fun initSocket() {
        socketRepository.connect()

        viewModelScope.launch {
            socketRepository.observeEventIds().collect { eventId ->
                if (eventId != 0L) {
                    triggerBlink(eventId)
                }
            }
        }
    }

    private fun triggerBlink(eventId: Long) {
        viewModelScope.launch {
            _blinkingEventIds.update { currentSet ->
                currentSet + eventId
            }

            delay(3000)

            _blinkingEventIds.update { currentSet ->
                currentSet - eventId
            }
        }
    }

    fun refresh() {
        loadEvents()
    }

    override fun onCleared() {
        super.onCleared()
        socketRepository.disconnect()
    }
}