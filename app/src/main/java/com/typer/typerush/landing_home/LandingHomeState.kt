package com.typer.typerush.landing_home

import com.typer.typerush.navigation.BottomBarScreen

data class LandingHomeState(
    val selectedRoute: BottomBarScreen = BottomBarScreen.Practice,
    val isLoading: Boolean = false,
    val error: String? = null,
    val showLogoutDialog: Boolean = false
)
