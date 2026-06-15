package ru.grinin.winlineapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import ru.grinin.winlineapp.presentation.screens.eventdetails.eventDetailsScreen
import ru.grinin.winlineapp.presentation.screens.eventdetails.navigateToEventDetails
import ru.grinin.winlineapp.presentation.screens.events.eventsScreen
import ru.grinin.winlineapp.presentation.screens.gifts.GiftsRoute
import ru.grinin.winlineapp.presentation.screens.gifts.giftsScreen

@Composable
fun MainNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    startDestination: Any = GiftsRoute,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        eventsScreen(navController::navigateToEventDetails)
        eventDetailsScreen()
        giftsScreen()
    }
}