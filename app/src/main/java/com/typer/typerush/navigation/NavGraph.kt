package com.typer.typerush.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.typer.typerush.auth.presentation.AuthScreen
import com.typer.typerush.create_contest.presentation.CreateContestScreen
import com.typer.typerush.landing_home.LandingHomeScreen
import com.typer.typerush.splash.SplashScreen
import com.typer.typerush.typetest.presentation.TypeTestScreen
import com.typer.typerush.waiting_page.presentation.WaitingScreen

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
    composable(route = "${Screen.TypeTest.route}/{id}") {
        val id = it.arguments?.getString("id") ?: ""
        TypeTestScreen(id)
    }
    composable(route = Screen.CreateContest.route) {
        CreateContestScreen()
    }
    composable(route = Screen.Waiting.route) {
        WaitingScreen()
    }
}