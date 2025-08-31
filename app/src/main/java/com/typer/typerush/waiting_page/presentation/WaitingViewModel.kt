package com.typer.typerush.waiting_page.presentation

import android.util.Log
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.typer.typerush.core.CurrentContest
import com.typer.typerush.navigation.NavigationManager
import com.typer.typerush.navigation.Screen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class WaitingViewModel(
    private val navigationManager: NavigationManager,
    private val currentContest: CurrentContest,
): ViewModel() {

    private val _state = MutableStateFlow(
        WaitingState(
            isConnected = true,
            contestModel = currentContest.currentContest.value
        ))
    val state = _state.asStateFlow()

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

    private fun onBackPressed() {
        viewModelScope.launch {
            navigationManager.navigateBack()
        }
    }

    private fun dismissError() {
        _state.value = _state.value.copy(
            errorMessage = null
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
        Log.d("WaitingViewModel", "onStartPressed: ${_state.value.contestModel}")
        if (_state.value.isConnected.not()) {
            _state.value = _state.value.copy(
                errorMessage = "Not connected to server"
            )
            return
        }
        if (_state.value.isStarting) {
            _state.value = _state.value.copy(
                errorMessage = "Already starting"
            )
            return
        }
        _state.update { it.copy(showStartDialog = true) }
    }

    private fun onActuallyStarted() {
        _state.update { it.copy(isStarting = true, showStartDialog = false) }
        viewModelScope.launch {
            navigationManager.navigateTo(Screen.TypeTest.route+"/")
        }
    }
}