package ru.grinin.winlineapp.utils

import ru.grinin.winlineapp.R
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException


class ErrorMapper(
    private val resourceManager: ResourceManager
) {

    fun getMessage(throwable: Throwable): String {
        return when (throwable) {
            is HttpException -> handleHttpException(throwable)
            is SocketTimeoutException -> resourceManager.getString(R.string.error_timeout)
            is UnknownHostException -> resourceManager.getString(R.string.error_no_internet)
            is IOException -> resourceManager.getString(R.string.error_connection)
            else -> throwable.message ?: resourceManager.getString(R.string.unknown_error)
        }
    }

    private fun handleHttpException(e: HttpException): String {
        return when (e.code()) {
            400 -> resourceManager.getString(R.string.error_bad_request)
            401 -> resourceManager.getString(R.string.error_unauthorized)
            403 -> resourceManager.getString(R.string.error_forbidden)
            404 -> resourceManager.getString(R.string.error_not_found)
            408 -> resourceManager.getString(R.string.error_timeout)
            429 -> resourceManager.getString(R.string.error_too_many_requests)
            500, 502, 503, 504 -> resourceManager.getString(R.string.error_server)
            else -> resourceManager.getString(R.string.error_network)
        }
    }


}