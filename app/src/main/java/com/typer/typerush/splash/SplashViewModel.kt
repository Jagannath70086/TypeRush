package com.typer.typerush.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.typer.typerush.auth.domain.repository.UserRepository
import com.typer.typerush.core.either.Either
import com.typer.typerush.navigation.NavigationManager
import com.typer.typerush.navigation.Screen
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SplashViewModel(
    private val navigationManager: NavigationManager,
    private val userRepository: UserRepository
): ViewModel() {
    private val _state = MutableStateFlow(SplashState())
    val state: StateFlow<SplashState> = _state.asStateFlow()

    fun onIntent(intent: SplashIntent) {
        when(intent) {
            is SplashIntent.FetchAppInfo -> fetchAppInfo()
            is SplashIntent.CheckUserStatus -> checkUserStatus()
        }
    }

    private fun fetchAppInfo() {
        _state.value = _state.value.copy(currentAction = SplashAction.FetchingAppInfo)
        viewModelScope.launch {
            delay(2000L)
            _state.value = _state.value.copy(currentAction = SplashAction.Done)
            onIntent(SplashIntent.CheckUserStatus)
        }
    }

    private fun checkUserStatus() {
        _state.value = _state.value.copy(currentAction = SplashAction.CheckingUserStatus)
        viewModelScope.launch {
            val result = userRepository.getUser()
            when (result) {
                is Either.Right -> navigationManager.navigateToAndClearBackStack(Screen.LandingHome.route)
                is Either.Left -> navigationManager.navigateToAndClearBackStack(Screen.SignIn.route)
            }
        }
    }
}