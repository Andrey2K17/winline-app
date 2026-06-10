package ru.grinin.winlineapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import ru.grinin.winlineapp.navigation.MainNavHost
import ru.grinin.winlineapp.ui.theme.WinlineAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            WinlineAppTheme {
                WinlineApp()
            }
        }
    }
}

@Composable
fun WinlineApp(
    navController: NavHostController = rememberNavController()
) {
    MainNavHost(
        navController = navController
    )
}