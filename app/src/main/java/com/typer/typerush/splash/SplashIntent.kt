package com.typer.typerush.splash

sealed interface SplashIntent {
    data object FetchAppInfo : SplashIntent
    data object CheckUserStatus : SplashIntent
}
