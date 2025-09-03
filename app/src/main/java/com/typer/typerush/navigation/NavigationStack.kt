package com.typer.typerush.navigation

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.typer.typerush.core.session.SessionManager
import org.koin.compose.koinInject

@Composable
fun NavigationStack(
    modifier: Modifier = Modifier,
    navigationManager: NavigationManager = koinInject(),
    sessionManager: SessionManager = koinInject()
) {
    val navController = rememberNavController()
    val navigationEvent by navigationManager.navigationEvent.collectAsState(initial = null)
    val user by sessionManager.currentUser.collectAsState()

    var currentRoute by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(navigationEvent) {
        navigationEvent?.let { event ->
            when(event) {
                is NavigationEvent.NavigateTo -> navController.navigate(event.destination)
                is NavigationEvent.NavigateToAndPopBackStack -> {
                    for (i in 1..event.times) {
                        navController.popBackStack()
                    }
                    navController.navigate(event.destination)
                }
                is NavigationEvent.NavigateBack -> {
                    if (currentRoute != Screen.TypeTest.route) {
                        navController.popBackStack()
                    }
                }
                is NavigationEvent.NavigateToAndClearBackStack -> {
                    navController.navigate(event.destination) {
                        popUpTo(0) {
                            inclusive = true
                        }
                    }
                }
            }
            navigationManager.clearNavigationEvent()
        }
    }

    LaunchedEffect(navController) {
        navController.currentBackStackEntryFlow.collect { backStackEntry ->
            currentRoute = backStackEntry.destination.route
            Log.d("NavigationStack", "Current route: $currentRoute")
        }
    }

    LaunchedEffect(user) {
        if (user == null) {
            navController.navigate(Screen.Splash.route) {
                popUpTo(0) {
                    inclusive = true
                }
            }
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
                contentWindowInsets = WindowInsets(0.dp)
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Splash.route,
            modifier = modifier.padding(innerPadding)
        ) {
            navGraph()
        }
    }
}