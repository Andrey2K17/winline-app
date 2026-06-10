package ru.grinin.winlineapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.grinin.winlineapp.R
import ru.grinin.winlineapp.domain.repository.EventRepository
import ru.grinin.winlineapp.domain.repository.SocketRepository
import ru.grinin.winlineapp.presentation.mapper.EventUiMapper
import ru.grinin.winlineapp.presentation.state.BlinkingState
import ru.grinin.winlineapp.presentation.state.EventsUiState
import ru.grinin.winlineapp.utils.ResourceManager
import kotlin.coroutines.cancellation.CancellationException

class EventsViewModel(
    private val eventRepository: EventRepository,
    private val socketRepository: SocketRepository,
    private val eventUiMapper: EventUiMapper,
    private val resourceManager: ResourceManager,
) : ViewModel() {

    private val _uiState = MutableStateFlow<EventsUiState>(EventsUiState.Loading)
    val uiState: StateFlow<EventsUiState> = _uiState.asStateFlow()

    private val _blinkingState = MutableStateFlow(BlinkingState())
    val blinkingState: StateFlow<BlinkingState> = _blinkingState.asStateFlow()

    init {
        loadEvents()
        initSocket()
    }

    private fun loadEvents() {
        viewModelScope.launch {
            _uiState.value = EventsUiState.Loading
            try {
                eventRepository.refreshEvents()
                val pagingData = eventRepository.getLiveEvents()
                    .map { pagingData ->
                        pagingData.map { entity ->
                            eventUiMapper.toUiModel(entity)
                        }
                    }
                    .cachedIn(viewModelScope)
                _uiState.value = EventsUiState.Success(pagingData)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _uiState.value = EventsUiState.Error(
                    e.message ?: resourceManager.getString(R.string.unknown_error)
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
            _blinkingState.update { state ->
                state.addBlinkingEvent(eventId)
            }

            delay(3000)

            _blinkingState.update { state ->
                state.removeBlinkingEvent(eventId)
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

val eventsViewModelModule = module {
    viewModel {
        EventsViewModel(
            eventRepository = get(),
            socketRepository = get(),
            eventUiMapper = get(),
            resourceManager = get(),
        )
    }
    single {
        EventUiMapper(
            sportsCache = get(),
        )
    }
}