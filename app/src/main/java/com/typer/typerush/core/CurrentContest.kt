package com.typer.typerush.core

import com.typer.typerush.create_contest.domain.models.ContestModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class CurrentContest {
    private val _currentContest = MutableStateFlow<ContestModel?>(null)
    val currentContest: StateFlow<ContestModel?> = _currentContest.asStateFlow()

    fun setCurrentContest(contest: ContestModel?) {
        _currentContest.value = contest
    }
}