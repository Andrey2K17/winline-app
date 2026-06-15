package ru.grinin.winlineapp.presentation.screens.gifts

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
object GiftsRoute

fun NavGraphBuilder.giftsScreen() {
    composable<GiftsRoute> {
        GiftsScreen()
    }
}