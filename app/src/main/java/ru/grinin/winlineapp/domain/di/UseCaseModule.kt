package ru.grinin.winlineapp.domain.di

import org.koin.dsl.module
import ru.grinin.winlineapp.domain.usecase.RemoveBlinkingUseCase
import ru.grinin.winlineapp.domain.usecase.UpdateOutcomeOddsUseCase
import ru.grinin.winlineapp.domain.utils.EventDetailsHelper

val useCaseModule = module {
    single { EventDetailsHelper() }
    single { UpdateOutcomeOddsUseCase(get()) }
    single { RemoveBlinkingUseCase(get()) }
}