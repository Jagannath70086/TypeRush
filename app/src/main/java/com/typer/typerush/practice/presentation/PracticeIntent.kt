package com.typer.typerush.practice.presentation

sealed interface PracticeIntent{
    data object GetPracticeCards : PracticeIntent
    data object Retry : PracticeIntent
    data class FilterPracticeCards(val query: String) : PracticeIntent
    data object ClearFilter : PracticeIntent
    data object DismissError : PracticeIntent
    data class StartTypeTest(val id: String) : PracticeIntent
}