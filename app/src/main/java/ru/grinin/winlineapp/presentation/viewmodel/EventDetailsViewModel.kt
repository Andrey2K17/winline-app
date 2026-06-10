package ru.grinin.winlineapp.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.grinin.winlineapp.data.models.remote.eventdetails.OddsUpdate
import ru.grinin.winlineapp.domain.repository.EventRepository
import ru.grinin.winlineapp.domain.repository.SocketRepository
import ru.grinin.winlineapp.domain.usecase.RemoveBlinkingUseCase
import ru.grinin.winlineapp.domain.usecase.UpdateOutcomeOddsUseCase
import ru.grinin.winlineapp.presentation.mapper.EventDetailsUiMapper
import ru.grinin.winlineapp.presentation.models.EventDetailsVO
import ru.grinin.winlineapp.presentation.state.EventDetailsUiState
import ru.grinin.winlineapp.utils.errorMapper.EventDetailsErrorMapper
import kotlin.coroutines.cancellation.CancellationException

private const val EVENT_ID_KEY = "eventId"

class EventDetailsViewModel(
    private val eventRepository: EventRepository,
    private val socketRepository: SocketRepository,
    private val eventDetailsMapper: EventDetailsUiMapper,
    private val errorMapper: EventDetailsErrorMapper,
    private val updateOutcomeOddsUseCase: UpdateOutcomeOddsUseCase,
    private val removeBlinkingUseCase: RemoveBlinkingUseCase,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val eventId: Long = checkNotNull(savedStateHandle[EVENT_ID_KEY])

    private val _uiState = MutableStateFlow<EventDetailsUiState>(EventDetailsUiState.Loading)
    val uiState: StateFlow<EventDetailsUiState> = _uiState.asStateFlow()

    private var currentEvent: EventDetailsVO? = null

    init {
        loadEventDetails()
        observeSocketEvents()
    }

    private fun loadEventDetails() {
        viewModelScope.launch {
            _uiState.value = EventDetailsUiState.Loading
            try {
                val event = eventRepository.getEventDetails(eventId)
                currentEvent = eventDetailsMapper.toUiModel(event).also {
                    _uiState.value =
                        EventDetailsUiState.Success(it)
                }

            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _uiState.value = EventDetailsUiState.Error(errorMapper.getMessage(e))
            }
        }
    }

    fun refresh() {
        loadEventDetails()
    }

    private fun observeSocketEvents() {
        viewModelScope.launch {
            socketRepository.observeOddsUpdates().collect { updates ->
                if (shouldProcessUpdates(updates)) {
                    handleOutcomeUpdate(updates)
                }
            }
        }
    }

    private fun shouldProcessUpdates(updates: List<OddsUpdate>): Boolean {
        return updates.isNotEmpty() &&
                updates.first().eventId == eventId &&
                currentEvent != null
    }

    private suspend fun handleOutcomeUpdate(updates: List<OddsUpdate>) {
        var event = currentEvent ?: return

        updates.forEach { update ->
            event = updateOutcomeOddsUseCase(
                event = event,
                marketKey = update.marketKey,
                outcomeId = update.outcomeId,
                newOdds = update.newOdds
            )
        }

        currentEvent = event
        _uiState.value = EventDetailsUiState.Success(event)

        delay(3000)

        updates.forEach { update ->
            event = removeBlinkingUseCase(
                event = event,
                marketKey = update.marketKey,
                outcomeId = update.outcomeId
            )
        }

        currentEvent = event
        _uiState.value = EventDetailsUiState.Success(event)
    }
}