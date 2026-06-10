package ru.grinin.winlineapp.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.grinin.winlineapp.domain.repository.EventRepository
import ru.grinin.winlineapp.presentation.mapper.EventDetailsMapper
import ru.grinin.winlineapp.presentation.state.EventDetailsUiState
import ru.grinin.winlineapp.utils.errorMapper.EventDetailsErrorMapper
import kotlin.coroutines.cancellation.CancellationException

private const val EVENT_ID_KEY = "eventId"

class EventDetailsViewModel(
    private val eventRepository: EventRepository,
    private val eventDetailsMapper: EventDetailsMapper,
    private val errorMapper: EventDetailsErrorMapper,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val eventId: Long = checkNotNull(savedStateHandle[EVENT_ID_KEY])

    private val _uiState = MutableStateFlow<EventDetailsUiState>(EventDetailsUiState.Loading)
    val uiState: StateFlow<EventDetailsUiState> = _uiState.asStateFlow()

    init {
        loadEventDetails()
    }

    private fun loadEventDetails() {
        viewModelScope.launch {
            _uiState.value = EventDetailsUiState.Loading
            try {
                val event = eventRepository.getEventDetails(eventId)
                _uiState.value =
                    EventDetailsUiState.Success(eventDetailsMapper.toUiModel(event))

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
}

val eventDetailsViewModelModule = module {
    viewModel { params ->
        EventDetailsViewModel(
            eventRepository = get(),
            eventDetailsMapper = get(),
            errorMapper = get(),
            savedStateHandle = params.get()
        )
    }
}