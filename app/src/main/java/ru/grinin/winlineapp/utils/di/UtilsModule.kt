package ru.grinin.winlineapp.utils.di

import org.koin.dsl.module
import ru.grinin.winlineapp.utils.errorMapper.ErrorMapper
import ru.grinin.winlineapp.utils.ResourceManager
import ru.grinin.winlineapp.utils.ResourceManagerImpl
import ru.grinin.winlineapp.utils.TimerManager
import ru.grinin.winlineapp.utils.errorMapper.EventDetailsErrorMapper

val utilsModule = module {
    single<ResourceManager> { ResourceManagerImpl(context = get()) }
    single { ErrorMapper(resourceManager = get()) }
    single { EventDetailsErrorMapper(resourceManager = get(), errorMapper = get()) }
    single { TimerManager() }
}