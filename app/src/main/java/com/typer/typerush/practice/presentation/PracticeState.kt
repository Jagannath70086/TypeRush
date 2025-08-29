package com.typer.typerush.practice.presentation

import com.typer.typerush.practice.domain.models.PracticeCardModel

data class PracticeState(
    val isLoading: Boolean = false,
    val practiceCards: List<PracticeCardModel> = emptyList(),
    val filteredPracticeCards: List<PracticeCardModel> = emptyList(),
    val error: String? = null,
    val selectedFilter: String = "All"
)