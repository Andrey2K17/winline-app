package ru.grinin.winlineapp.data.di

import org.koin.dsl.module
import ru.grinin.winlineapp.data.datasource.local.EventLocalDataSource
import ru.grinin.winlineapp.data.datasource.local.impl.EventLocalDataSourceImpl
import ru.grinin.winlineapp.data.datasource.remote.EventRemoteDataSource
import ru.grinin.winlineapp.data.datasource.remote.impl.EventRemoteDataSourceImpl

val eventRemoteDataSourceModule = module {
    single <EventRemoteDataSource> {
        EventRemoteDataSourceImpl(
            apiService = get()
        )
    }

    single <EventLocalDataSource> {
        EventLocalDataSourceImpl(
            eventDao = get()
        )
    }
}