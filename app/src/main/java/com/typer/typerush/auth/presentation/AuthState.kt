package com.typer.typerush.auth.presentation

data class AuthState(
    val isLoading: Boolean = false,
    val error: String? = null,
)
