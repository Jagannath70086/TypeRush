package com.typer.typerush.compete.presentation

import com.typer.typerush.compete.domain.models.ContestCardModel

data class CompeteState(
    val isConnected: Boolean = false,
    val isLoading: Boolean = false,
    val contestCards: List<ContestCardModel> = emptyList(),
    val filteredContestCards: List<ContestCardModel> = emptyList(),
    val error: String? = null,
    val selectedFilter: String = "All",
    val contestCodeWritingText: String = "",
    val success: String? = null
)
