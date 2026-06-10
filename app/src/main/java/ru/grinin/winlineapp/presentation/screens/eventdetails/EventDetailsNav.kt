package ru.grinin.winlineapp.presentation.screens.eventdetails

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data class EventDetailsRoute(val eventId: Long)

fun NavController.navigateToEventDetails(eventId: Long) {
    navigate(EventDetailsRoute(eventId))
}

fun NavGraphBuilder.eventDetailsScreen() {
    composable<EventDetailsRoute> {
        EventDetailsScreen()
    }
}