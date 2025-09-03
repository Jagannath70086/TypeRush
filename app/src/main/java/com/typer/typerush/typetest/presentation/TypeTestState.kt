package com.typer.typerush.typetest.presentation

import com.typer.typerush.typetest.domain.models.GameInfoModel

data class TypeTestState(
    val isLoading: Boolean = false,
    val hasSubmitted: Boolean = false,
    val isConnected: Boolean = false,
    val type: String = "",
    val input: String = "",
    val currentPosition: Int = 0,
    val timerValue: String = "02:30",
    val time: Long = 0,
    val hasTimerStarted: Boolean = false,
    val gameInfo: GameInfoModel?,
    val wpm: Int = 0,
    val accuracy: Double = 0.0,
    val error: String? = null,
    val successMessage: String? = null
)
