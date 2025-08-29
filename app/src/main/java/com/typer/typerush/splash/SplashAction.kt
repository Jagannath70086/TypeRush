package com.typer.typerush.splash

sealed class SplashAction(val message: String) {
    data object FetchingAppInfo : SplashAction("Fetching app info...")
    data object CheckingUserStatus : SplashAction("Checking user status...")
    data object Done : SplashAction("Done")
}
