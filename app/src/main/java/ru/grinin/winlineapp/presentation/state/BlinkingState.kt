package ru.grinin.winlineapp.presentation.state

import kotlinx.collections.immutable.PersistentSet
import kotlinx.collections.immutable.persistentSetOf

data class BlinkingState(
    val blinkingEventIds: PersistentSet<Long> = persistentSetOf()
) {
    fun addBlinkingEvent(eventId: Long): BlinkingState {
        return copy(blinkingEventIds = blinkingEventIds.adding(eventId))
    }

    fun removeBlinkingEvent(eventId: Long): BlinkingState {
        return copy(blinkingEventIds = blinkingEventIds.removing(eventId))
    }
}
