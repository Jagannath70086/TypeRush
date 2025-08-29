package com.typer.typerush.auth.presentation

sealed interface AuthIntent {
    data object SignInWithGoogle: AuthIntent
    data object SignOut: AuthIntent
    data object DismissError: AuthIntent
}