package ru.grinin.winlineapp.utils

import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ru.grinin.winlineapp.R
import ru.grinin.winlineapp.presentation.models.SnowParams
import java.util.Locale

fun Int.toHmsFormat(): String {
    val hours = this / 3600
    val minutes = (this % 3600) / 60
    val seconds = this % 60
    return String.format(Locale.US, "%02d:%02d:%02d", hours, minutes, seconds)
}

fun Int.toSnowParams(cardSize: Dp): SnowParams {
    val column = this
    return SnowParams(
        imgRes = when (column) {
            0 -> R.drawable.snow_left
            1 -> R.drawable.snow_center
            else -> R.drawable.snow_right
        },
        offsetY = if (column == 1) -cardSize * 0.07f else -cardSize * 0.03f,
        offsetX = if (column == 0) -cardSize * 0.03f else 0.dp,
        alignment = when (column) {
            0 -> Alignment.TopStart
            1 -> Alignment.TopCenter
            else -> Alignment.TopEnd
        }
    )
}