package ru.grinin.winlineapp.data.di

import androidx.room.Room
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import ru.grinin.winlineapp.data.database.EventDatabase

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            EventDatabase::class.java,
            "winline_database"
        ).build()
    }

    single { get<EventDatabase>().eventDao() }
}