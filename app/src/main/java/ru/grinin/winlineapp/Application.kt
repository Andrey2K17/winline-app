package ru.grinin.winlineapp

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import ru.grinin.winlineapp.data.di.databaseModule
import ru.grinin.winlineapp.data.di.eventMapperModule
import ru.grinin.winlineapp.data.di.eventRemoteDataSourceModule
import ru.grinin.winlineapp.data.di.eventRepositoryModule
import ru.grinin.winlineapp.data.di.networkModule
import ru.grinin.winlineapp.presentation.viewmodel.eventsViewModelModule
import ru.grinin.winlineapp.utils.di.utilsModule

class Application: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@Application)
            modules(
                databaseModule,
                networkModule,
                eventRemoteDataSourceModule,
                eventRepositoryModule,
                eventsViewModelModule,
                eventMapperModule,
                utilsModule,
            )
        }
    }
}