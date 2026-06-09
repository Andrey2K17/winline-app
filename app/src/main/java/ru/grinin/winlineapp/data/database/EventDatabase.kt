package ru.grinin.winlineapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.grinin.winlineapp.data.database.dao.EventDao
import ru.grinin.winlineapp.data.models.local.EventEntity

@Database(
    entities = [EventEntity::class],
    version = 1,
    exportSchema = false
)
abstract class EventDatabase : RoomDatabase() {
    abstract fun eventDao(): EventDao
}