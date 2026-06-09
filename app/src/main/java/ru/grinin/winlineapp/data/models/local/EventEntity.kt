package ru.grinin.winlineapp.data.models.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "events")
data class EventEntity(
    @PrimaryKey
    val id: Long,
    val scheduled: Long,
    val homeTeamName: String,
    val homeTeamImage: String?,
    val awayTeamName: String,
    val awayTeamImage: String?,
)