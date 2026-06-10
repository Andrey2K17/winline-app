package ru.grinin.winlineapp.data.di

import org.koin.dsl.module
import ru.grinin.winlineapp.data.repository.EventRepositoryImpl
import ru.grinin.winlineapp.data.repository.SocketRepositoryImpl
import ru.grinin.winlineapp.domain.repository.EventRepository
import ru.grinin.winlineapp.domain.repository.SocketRepository

val eventRepositoryModule = module {
    single <EventRepository> {
        EventRepositoryImpl(
            remoteDataSource = get(),
            localDataSource = get(),
            dataToEntityMapper = get(),
            sportsCache = get(),
        )
    }
    single <SocketRepository> {
        SocketRepositoryImpl(
            socketService = get(),
            json = get(),
        )
    }
}