package com.typer.typerush.landing_home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.typer.typerush.auth.domain.repository.AuthRepository
import com.typer.typerush.core.either.Either
import com.typer.typerush.navigation.BottomBarScreen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LandingHomeViewModel(
    private val authRepository: AuthRepository
): ViewModel() {
    private val _state = MutableStateFlow(LandingHomeState())
    val state = _state.asStateFlow()

    fun onIntent(intent: LandingHomeIntent) {
        when(intent) {
            is LandingHomeIntent.NavigateToBottomBarScreen -> {
                navigateToBottomBarScreen(intent.bottomBarScreen)
            }
            LandingHomeIntent.ShowLogoutDialog -> {
                _state.value = _state.value.copy(
                    showLogoutDialog = true
                )
            }
            LandingHomeIntent.DismissError -> {
                _state.value = _state.value.copy(
                    error = null
                )
            }
            LandingHomeIntent.DismissLogoutDialog -> {
                _state.value = _state.value.copy(
                    showLogoutDialog = false
                )
            }
            LandingHomeIntent.Logout -> signOut()
        }
    }

    private fun navigateToBottomBarScreen(bottomBarScreen: BottomBarScreen) {
        _state.update { it.copy(selectedRoute = bottomBarScreen) }
    }

    private fun signOut() {
        _state.value = _state.value.copy(isLoading = true)

        viewModelScope.launch {
            when (val result = authRepository.signOut()) {
                is Either.Right -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = null,
                        showLogoutDialog = false
                    )
                }
                is Either.Left -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = result.error.message,
                        showLogoutDialog = false
                    )
                }
            }
        }
    }
}