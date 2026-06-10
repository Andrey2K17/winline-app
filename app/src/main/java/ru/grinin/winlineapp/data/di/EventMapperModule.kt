package ru.grinin.winlineapp.data.di

import org.koin.dsl.module
import ru.grinin.winlineapp.data.cache.SportsCache
import ru.grinin.winlineapp.data.mapper.DataToEntityMapper
import ru.grinin.winlineapp.presentation.mapper.EventDetailsMapper

val eventMapperModule = module {
    single<DataToEntityMapper> { DataToEntityMapper() }
    single { SportsCache() }
    single { EventDetailsMapper() }
}