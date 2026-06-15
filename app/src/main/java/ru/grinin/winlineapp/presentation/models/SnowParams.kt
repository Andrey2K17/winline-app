package ru.grinin.winlineapp.presentation.models

import androidx.annotation.DrawableRes
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.Dp

data class SnowParams(
    @param:DrawableRes val imgRes: Int,
    val offsetY: Dp,
    val offsetX: Dp,
    val alignment: Alignment,
)
