package ru.grinin.winlineapp.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class TimerManager {
    private val _remainingSeconds = MutableStateFlow<Int?>(null)
    val remainingSeconds = _remainingSeconds.asStateFlow()

    private var endTimeMillis: Long? = null
    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    private var updateJob: kotlinx.coroutines.Job? = null

    fun startTimer(durationSeconds: Int) {
        endTimeMillis = System.currentTimeMillis() + durationSeconds * 1000
        start()
    }

    private fun start() {
        updateJob?.cancel()
        updateJob = scope.launch {
            while (isActive) {
                val remaining = endTimeMillis?.let { end ->
                    val now = System.currentTimeMillis()
                    ((end - now) / 1000).toInt().coerceAtLeast(0)
                } ?: break

                _remainingSeconds.value = remaining
                if (remaining <= 0) {

                    updateJob?.cancel()
                    updateJob = null
                    endTimeMillis = null
                    break
                }
                delay(1000)
            }
        }
    }

    fun stopTimer() {
        updateJob?.cancel()
        updateJob = null
        endTimeMillis = null
        _remainingSeconds.value = null
    }
}