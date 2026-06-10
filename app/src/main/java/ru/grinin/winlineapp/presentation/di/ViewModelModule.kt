package ru.grinin.winlineapp.presentation.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.grinin.winlineapp.presentation.viewmodel.EventDetailsViewModel
import ru.grinin.winlineapp.presentation.viewmodel.EventsViewModel

val viewModelModule = module {
    viewModel { params ->
        EventDetailsViewModel(
            eventRepository = get(),
            socketRepository = get(),
            eventDetailsMapper = get(),
            errorMapper = get(),
            updateOutcomeOddsUseCase = get(),
            removeBlinkingUseCase = get(),
            savedStateHandle = params.get()
        )
    }

    viewModel {
        EventsViewModel(
            eventRepository = get(),
            socketRepository = get(),
            eventUiMapper = get(),
            errorMapper = get(),
        )
    }
}