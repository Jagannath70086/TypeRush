package com.typer.typerush.waiting_page.presentation

interface WaitingEvent {
    object NewParticipantJoined : WaitingEvent
    object ContestStarted : WaitingEvent
    data class Error(val message: String) : WaitingEvent
    data object UserFinishedContest : WaitingEvent
}