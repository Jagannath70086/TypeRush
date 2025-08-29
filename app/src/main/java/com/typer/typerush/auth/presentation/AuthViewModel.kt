package com.typer.typerush.auth.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.typer.typerush.auth.domain.repository.AuthRepository
import com.typer.typerush.core.either.Either
import com.typer.typerush.navigation.NavigationManager
import com.typer.typerush.navigation.Screen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthViewModel(
    private val authRepository: AuthRepository,
    private val navigationManager: NavigationManager
): ViewModel() {
    private val _state = MutableStateFlow(AuthState())
    val state: StateFlow<AuthState> = _state.asStateFlow()

    fun onIntent(intent: AuthIntent) {
        when(intent) {
            is AuthIntent.SignInWithGoogle -> signInWithGoogle()
            is AuthIntent.SignOut -> signOut()
            is AuthIntent.DismissError -> dismissError()
        }
    }

    private fun signInWithGoogle() {
        _state.value = _state.value.copy(isLoading = true)

        viewModelScope.launch {
            when (val result = authRepository.signInWithGoogle()) {
                is Either.Right -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = null
                    )
                    navigationManager.navigateTo(Screen.LandingHome.route)
                }
                is Either.Left -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = result.error.message,
                    )
                }
            }
        }
    }

    private fun signOut() {
        _state.value = _state.value.copy(isLoading = true)

        viewModelScope.launch {
            when (val result = authRepository.signOut()) {
                is Either.Right -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = null
                    )
                    navigationManager.navigateToAndClearBackStack(Screen.SignIn.route)
                }
                is Either.Left -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = result.error.message
                    )
                }
            }
        }
    }

    private fun dismissError() {
        _state.value = _state.value.copy(error = null)
    }
}