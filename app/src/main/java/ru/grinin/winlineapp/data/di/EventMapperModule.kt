package ru.grinin.winlineapp.data.di

import org.koin.dsl.module
import ru.grinin.winlineapp.data.cache.SportsCache
import ru.grinin.winlineapp.data.mapper.DataToEntityMapper

val eventMapperModule = module {
    single<DataToEntityMapper> { DataToEntityMapper() }
    single { SportsCache() }
}