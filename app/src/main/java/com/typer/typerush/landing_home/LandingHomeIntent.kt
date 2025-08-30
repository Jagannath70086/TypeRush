package com.typer.typerush.landing_home

import com.typer.typerush.navigation.BottomBarScreen

sealed interface LandingHomeIntent {
    data class NavigateToBottomBarScreen(val bottomBarScreen: BottomBarScreen): LandingHomeIntent
    data object ShowLogoutDialog: LandingHomeIntent
    data object Logout: LandingHomeIntent
    data object DismissLogoutDialog: LandingHomeIntent
    data object DismissError: LandingHomeIntent
}