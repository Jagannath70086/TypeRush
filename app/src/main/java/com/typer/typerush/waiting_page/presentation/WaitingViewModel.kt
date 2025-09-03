package com.typer.typerush.waiting_page.presentation

import android.util.Log
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.typer.typerush.core.CurrentContest
import com.typer.typerush.core.CurrentGameProvider
import com.typer.typerush.core.either.Either
import com.typer.typerush.core.websocket.WebSocketEvent
import com.typer.typerush.navigation.NavigationManager
import com.typer.typerush.navigation.Screen
import com.typer.typerush.waiting_page.domain.repository.WaitingRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class WaitingViewModel(
    private val navigationManager: NavigationManager,
    private val currentContest: CurrentContest,
    private val waitingRepository: WaitingRepository,
    private val currentGame: CurrentGameProvider
): ViewModel() {

    private val _state = MutableStateFlow(
        WaitingState(
            isConnected = true,
            contestModel = currentContest.currentContest.value
        ))
    val state = _state.asStateFlow()

    init {
        observeEvents()
    }

    fun onIntent(intent: WaitingIntent) {
        when (intent) {
            is WaitingIntent.OnBackPressed -> onBackPressed()
            is WaitingIntent.OnStartPressed -> onStartPressed()
            is WaitingIntent.DismissError -> dismissError()
            is WaitingIntent.DismissSuccess -> dismissSuccess()
            is WaitingIntent.OnCodeCopied -> onCodeCopied(intent.clipboardManager)
            is WaitingIntent.OnActuallyStarted -> onActuallyStarted()
            is WaitingIntent.DismissStartDialog -> _state.update { it.copy(showStartDialog = false) }
        }
    }

    private fun observeEvents() {
        viewModelScope.launch {
            waitingRepository.getSocketEvents().collect { event ->
                when (event) {
                    is WebSocketEvent.Error -> {
                        _state.update { it.copy(error = event.message) }
                    }
                    WebSocketEvent.Connected -> {
                        _state.update { it.copy(isConnected = true) }
                    }
                    WebSocketEvent.Disconnected -> {
                        _state.update { it.copy(isConnected = false) }
                    }
                }
            }
        }

        viewModelScope.launch {
            waitingRepository.getWaitingEvents().collect { event ->
                when (event) {
                    is WaitingEvent.Error -> {
                        _state.update { it.copy(error = event.message, isLoading = false) }
                    }
                    is WaitingEvent.NewParticipantJoined -> {
                        _state.update {
                            it.copy(
                                contestModel = it.contestModel?.copy(
                                    players = currentContest.currentContest.value!!.players
                                )
                            )
                        }
                    }
                    is WaitingEvent.UserFinishedContest -> {
                        _state.update {
                            it.copy(
                                contestModel = it.contestModel?.copy(
                                    players = currentContest.currentContest.value!!.players
                                )
                            )
                        }
                    }
                    is WaitingEvent.ContestStarted -> {
                        _state.update {
                            it.copy(
                                hasStarted = true,
                                contestModel = it.contestModel?.copy(
                                    status = "Started"
                                )
                            )
                        }
                        navigateToGamePage()
                    }
                }
            }
        }
    }

    private fun navigateToGamePage() {
        viewModelScope.launch {
            delay(3000L)
            navigationManager.navigateTo("${Screen.TypeTest.route}/contest")
        }
    }

    private fun onBackPressed() {
        viewModelScope.launch {
            navigationManager.navigateBack()
        }
    }

    private fun dismissError() {
        _state.value = _state.value.copy(
            error = null
        )
    }

    private fun dismissSuccess() {
        _state.value = _state.value.copy(
            successMessage = null
        )
    }

    private fun onCodeCopied(clipboardManager: ClipboardManager) {
        clipboardManager.setText(AnnotatedString(_state.value.contestModel?.contestCode ?: "") )
        _state.value = _state.value.copy(
            successMessage = "Code copied to clipboard"
        )
    }

    private fun onStartPressed() {
        if (_state.value.isConnected.not()) {
            _state.value = _state.value.copy(
                error = "Not connected to server"
            )
            return
        }
        if (_state.value.isLoading) {
            _state.value = _state.value.copy(
                error = "Already starting"
            )
            return
        }
        _state.update { it.copy(showStartDialog = true) }
    }

    private fun onActuallyStarted() {
        _state.update { it.copy(isLoading = true, showStartDialog = false) }
        viewModelScope.launch {
            when (val result = waitingRepository.startRoom()) {
                is Either.Left -> {
                    _state.update { it.copy(error = result.error.message, isLoading = false) }
                }
                else -> {}
            }
        }
    }
}