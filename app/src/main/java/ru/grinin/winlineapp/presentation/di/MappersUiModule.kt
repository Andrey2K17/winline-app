package ru.grinin.winlineapp.presentation.di

import org.koin.dsl.module
import ru.grinin.winlineapp.presentation.mapper.EventDetailsUiMapper
import ru.grinin.winlineapp.presentation.mapper.EventUiMapper

val mappersUiModule = module {
    single { EventDetailsUiMapper() }
    single { EventUiMapper(sportsCache = get()) }
}