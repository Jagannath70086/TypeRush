package com.typer.typerush.core

import com.typer.typerush.typetest.domain.models.GameInfoModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class CurrentGameProvider {
    private val _currentGame = MutableStateFlow<GameInfoModel?>(null)
    val currentGame: StateFlow<GameInfoModel?> = _currentGame.asStateFlow()

    fun setCurrentGame(game: GameInfoModel?) {
        _currentGame.value = game
    }
}