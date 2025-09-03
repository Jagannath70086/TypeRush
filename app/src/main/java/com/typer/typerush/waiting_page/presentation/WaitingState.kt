package com.typer.typerush.waiting_page.presentation

import com.typer.typerush.create_contest.domain.models.ContestModel

data class WaitingState(
    val isLoading: Boolean = false,
    val hasStarted: Boolean = false,
    val contestModel: ContestModel? = null,
    val isConnected: Boolean = false,
    val error: String? = null,
    val successMessage: String? = null,
    val showStartDialog: Boolean = false
)
