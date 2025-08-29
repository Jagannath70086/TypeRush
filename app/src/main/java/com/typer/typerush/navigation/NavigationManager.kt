package com.typer.typerush.navigation

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class NavigationManager {
    private val _navigationEvent = MutableSharedFlow<NavigationEvent>(extraBufferCapacity = 1)
    val navigationEvent = _navigationEvent.asSharedFlow()

    suspend fun navigateTo(destination: String) {
        _navigationEvent.emit(NavigationEvent.NavigateTo(destination))
    }

    suspend fun navigateBack() {
        _navigationEvent.emit(NavigationEvent.NavigateBack)
    }

    suspend fun navigateToAndClearBackStack(destination: String) {
        _navigationEvent.emit(NavigationEvent.NavigateToAndClearBackStack(destination))
    }
}

sealed class NavigationEvent {
    data class NavigateTo(val destination: String) : NavigationEvent()
    object NavigateBack : NavigationEvent()
    data class NavigateToAndClearBackStack(val destination: String) : NavigationEvent()
}