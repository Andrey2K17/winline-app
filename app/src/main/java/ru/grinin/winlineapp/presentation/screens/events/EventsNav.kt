package ru.grinin.winlineapp.presentation.screens.events

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
object EventsRoute

fun NavGraphBuilder.eventsScreen(
    navigateToEventDetails: (Long) -> Unit,
) {
    composable<EventsRoute> {
        EventsScreen(
            navigateToDetails = navigateToEventDetails,
        )
    }
}