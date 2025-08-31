package com.typer.typerush.compete.presentation

sealed interface CompeteIntent {
    data object AttemptConnection : CompeteIntent
    data object AttemptReconnection : CompeteIntent
    data object GetCompeteCards : CompeteIntent
    data object JoinByContestCode : CompeteIntent
    data object CreateNewContest : CompeteIntent
    data class EnterWaitingPage(val contestCode: String) : CompeteIntent
    data class EnterContestCode(val contestCode: String) : CompeteIntent
    data class FilterPracticeCards(val query: String) : CompeteIntent
    data object DismissError : CompeteIntent
    data object DismissSuccess : CompeteIntent
}