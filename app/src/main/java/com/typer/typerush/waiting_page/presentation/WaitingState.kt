package com.typer.typerush.waiting_page.presentation

import com.typer.typerush.create_contest.domain.models.ContestModel

data class WaitingState(
    val isStarting: Boolean = false,
    val contestModel: ContestModel? = null,
    val isConnected: Boolean = false,
    val errorMessage: String? = null,
    val successMessage: String? = null,
    val showStartDialog: Boolean = false
)
