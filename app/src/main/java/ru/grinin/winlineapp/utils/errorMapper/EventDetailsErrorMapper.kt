package ru.grinin.winlineapp.utils.errorMapper

import ru.grinin.winlineapp.R
import ru.grinin.winlineapp.utils.ResourceManager

class EmptyEventDetailsException : Exception()

class EventDetailsErrorMapper(
    private val resourceManager: ResourceManager,
    private val errorMapper: ErrorMapper,
) {

    fun getMessage(throwable: Throwable): String {
        return when (throwable) {
            is EmptyEventDetailsException -> resourceManager.getString(R.string.empty_event_details)
            else -> errorMapper.getMessage(throwable)
        }
    }
}