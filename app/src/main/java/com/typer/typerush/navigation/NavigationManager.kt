package com.typer.typerush.navigation

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow

class NavigationManager {
    private val _navigationEvent = MutableStateFlow<NavigationEvent?>(null)
    val navigationEvent = _navigationEvent.asStateFlow()

    suspend fun navigateTo(destination: String) {
        _navigationEvent.emit(NavigationEvent.NavigateTo(destination))
    }

    suspend fun navigateToAndPopBackStack(destination: String, times: Int = 1) {
        _navigationEvent.emit(NavigationEvent.NavigateToAndPopBackStack(destination, times))
    }

    suspend fun navigateBack() {
        _navigationEvent.emit(NavigationEvent.NavigateBack)
    }

    suspend fun navigateToAndClearBackStack(destination: String) {
        _navigationEvent.emit(NavigationEvent.NavigateToAndClearBackStack(destination))
    }

    suspend fun clearNavigationEvent() {
        _navigationEvent.emit(null)
    }
}

sealed class NavigationEvent {
    data class NavigateTo(val destination: String) : NavigationEvent()
    data class NavigateToAndPopBackStack(val destination: String, val times: Int) : NavigationEvent()
    object NavigateBack : NavigationEvent()
    data class NavigateToAndClearBackStack(val destination: String) : NavigationEvent()
}