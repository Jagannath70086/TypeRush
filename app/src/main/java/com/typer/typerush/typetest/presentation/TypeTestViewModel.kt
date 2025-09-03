package com.typer.typerush.typetest.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.typer.typerush.core.session.SessionManager
import com.typer.typerush.core.websocket.WebSocketEvent
import com.typer.typerush.navigation.NavigationManager
import com.typer.typerush.navigation.Screen
import com.typer.typerush.core.CurrentGameProvider
import com.typer.typerush.core.either.Either
import com.typer.typerush.typetest.domain.models.SubmissionModel
import com.typer.typerush.typetest.domain.repository.TypeTestRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Locale

class TypeTestViewModel(
    private val currentGameProvider: CurrentGameProvider,
    private val typeTestRepository: TypeTestRepository,
    private val sessionManager: SessionManager,
    private val navigationManager: NavigationManager,
): ViewModel() {

    private val currentGame = currentGameProvider.currentGame.value!!
    private val _state = MutableStateFlow(TypeTestState(gameInfo = currentGame, time = currentGame.time * 60, timerValue = timerToString(currentGame.time * 60)))
    val state = _state.asStateFlow()

    init {
        observeEvents()
    }

    fun onIntent(intent: TypeTestIntent) {
        when (intent) {
            is TypeTestIntent.OnTypedValueChanged -> onValueChanged(intent.newValue)
            TypeTestIntent.DismissError -> _state.update { it.copy(error = null) }
            TypeTestIntent.DismissSuccess -> _state.update { it.copy(successMessage = null) }
            is TypeTestIntent.SetType -> _state.update { it.copy(type = intent.type) }
        }
    }

    private fun onValueChanged(newValue: String) {
        if (newValue.length == 1 && !state.value.hasTimerStarted) {
            startTimer()
            _state.update { it.copy(hasTimerStarted = true) }
        }
        if (newValue.length <= state.value.gameInfo!!.text.length) {
            _state.update { it.copy(
                input = newValue,
                currentPosition = newValue.length
            ) }
        }
        if (newValue.length == state.value.gameInfo!!.text.length) {
            submitGame()
        }
    }

    private fun submitGame() {
        _state.update { it.copy(
            isLoading = true
        ) }
        viewModelScope.launch {
            val submissionModel = SubmissionModel(
                contestId = state.value.gameInfo!!.id!!,
                userId = sessionManager.currentUser.value!!.firebaseId!!,
                wpm = state.value.wpm,
                accuracy = state.value.accuracy
            )
            val res = typeTestRepository.typeTestFinished(
                type = state.value.type,
                submissionModel = submissionModel
            )
            when (res) {
                is Either.Left -> {
                    _state.update { it.copy(error = res.error.message, isLoading = false) }
                }
                is Either.Right -> {
                    if (state.value.type == "practice") {
                        _state.update { it.copy(successMessage = res.value, isLoading = false) }
                        currentGameProvider.setCurrentGame(currentGame.copy(
                            result = submissionModel
                        ))
                        navigationManager.navigateToAndPopBackStack(Screen.Results.route)
                    }
                }
            }
        }
    }

    private fun startTimer() {
        viewModelScope.launch {
            delay(1000)
            _state.update { it.copy(time = it.time - 1) }
            _state.update { it.copy(timerValue = timerToString(it.time)) }
            if (state.value.time > 0 && !state.value.isLoading) {
                startTimer()
                calculateAndUpdateWPM()
                calculateAndUpdateAccuracy()
            }
            else if (state.value.time == 0L) {
                submitGame()
            }
        }
    }

    private fun calculateAndUpdateWPM() {
        val currentState = state.value
        val gameInfo = currentState.gameInfo ?: return _state.update {
            it.copy(wpm = 0)
        }

        val totalTimeInSeconds = gameInfo.time * 60
        val elapsedTimeInSeconds = totalTimeInSeconds - currentState.time
        val elapsedTimeInMinutes = elapsedTimeInSeconds / 60.0

        if (elapsedTimeInMinutes <= 0) return _state.update {
            it.copy(wpm = 0)
        }

        val correctCharacters = countCorrectCharacters(currentState.input, gameInfo.text)
        val incorrectCharacters = currentState.input.length - correctCharacters

        val netWpm = (((correctCharacters - incorrectCharacters).toDouble() / 5.0) / elapsedTimeInMinutes).toInt()

        _state.update { it.copy(wpm = maxOf(0, netWpm)) }
    }

    private fun calculateAndUpdateAccuracy() {
        val currentState = state.value
        val gameInfo = currentState.gameInfo ?: return
        val input = currentState.input

        if (input.isEmpty()) {
            _state.update { it.copy(accuracy = 100.0) }
            return
        }

        val correctCharacters = countCorrectCharacters(input, gameInfo.text)
        val rawAccuracy = (correctCharacters.toDouble() / input.length.toDouble()) * 100.0
        val accuracy = String.format(Locale.getDefault(),"%.2f", rawAccuracy).toDouble()

        _state.update { it.copy(accuracy = accuracy) }
    }


    private fun countCorrectCharacters(input: String, originalText: String): Int {
        var correctCount = 0
        val minLength = minOf(input.length, originalText.length)

        for (i in 0 until minLength) {
            if (input[i] == originalText[i]) {
                correctCount++
            }
        }

        return correctCount
    }

    private fun timerToString(time: Long): String {
        val minutes = time / 60
        val seconds = time % 60
        return String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds)
    }

    private fun observeEvents() {
        viewModelScope.launch {
            typeTestRepository.getSocketEvents().collect { event ->
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
            typeTestRepository.getTypeTestEvents().collect { event ->
                when (event) {
                    is TypeTestEvent.Error -> {
                        _state.update { it.copy(error = event.message, isLoading = false) }
                    }
                    is TypeTestEvent.Submitted -> {
                        _state.update { it.copy(hasSubmitted = true, isLoading = false) }
                        currentGameProvider.setCurrentGame(currentGame.copy(
                            result = SubmissionModel(
                                contestId = state.value.gameInfo!!.id!!,
                                userId = sessionManager.currentUser.value!!.firebaseId!!,
                                wpm = state.value.wpm,
                                accuracy = state.value.accuracy
                            )
                        ))
                        navigationManager.navigateToAndPopBackStack(Screen.Results.route, times = 2)
                    }
                }
            }
        }
    }

}