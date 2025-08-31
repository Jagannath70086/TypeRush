package com.typer.typerush.create_contest.presentation

sealed interface CreateContestIntent {
    data class OnTitleChanged(val title: String): CreateContestIntent
    data class OnTextSnippetChanged(val textSnippet: String): CreateContestIntent
    data class OnTagsChanged(val tags: String): CreateContestIntent
    data class OnDurationChanged(val duration: Int): CreateContestIntent
    data class OnMaxPlayersChanged(val maxPlayers: Int): CreateContestIntent
    data class OnDifficultyChanged(val difficulty: String): CreateContestIntent
    data object CreateContest: CreateContestIntent
    data object DismissError: CreateContestIntent
    data object OnBackPressed: CreateContestIntent
}