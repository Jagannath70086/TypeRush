package com.typer.typerush.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.typer.typerush.auth.presentation.AuthScreen
import com.typer.typerush.landing_home.LandingHomeScreen
import com.typer.typerush.splash.SplashScreen

fun NavGraphBuilder.navGraph() {
    composable(route = Screen.Splash.route) {
        SplashScreen()
    }
    composable(route = Screen.SignIn.route) {
        AuthScreen()
    }
    composable(route = Screen.LandingHome.route) {
        LandingHomeScreen()
    }
}