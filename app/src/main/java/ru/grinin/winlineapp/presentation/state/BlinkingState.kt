package ru.grinin.winlineapp.presentation.state

data class BlinkingState(
    val blinkingEventIds: Set<Long> = emptySet()
) {
    fun addBlinkingEvent(eventId: Long): BlinkingState {
        return copy(blinkingEventIds = blinkingEventIds + eventId)
    }

    fun removeBlinkingEvent(eventId: Long): BlinkingState {
        return copy(blinkingEventIds = blinkingEventIds - eventId)
    }
}
