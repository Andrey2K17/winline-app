package ru.grinin.winlineapp.presentation.models

data class EventVO(
    val id: Long,
    val homeTeamName: String,
    val awayTeamName: String,
    val fullPath: String,
    val isBlinking: Boolean = false,
)