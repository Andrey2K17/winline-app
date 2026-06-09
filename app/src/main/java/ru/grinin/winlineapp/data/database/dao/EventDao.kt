package ru.grinin.winlineapp.data.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.grinin.winlineapp.data.models.local.EventEntity

@Dao
interface EventDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(events: List<EventEntity>)

    @Query("DELETE FROM events")
    suspend fun clearAll()

    @Query("SELECT * FROM events ORDER BY scheduled ASC, homeTeamName ASC")
    fun getLiveEventsPagingSource(): PagingSource<Int, EventEntity>

    @Query("SELECT COUNT(*) FROM events")
    suspend fun getLiveEventsCount(): Int
}