package com.typer.typerush.create_contest.presentation

import com.typer.typerush.create_contest.domain.models.ContestModel

data class CreateContestState(
    val isConnected: Boolean = false,
    val contest: ContestModel,
    val error: String? = null,
    val isLoading: Boolean = false,
    val successMessage: String? = null
)