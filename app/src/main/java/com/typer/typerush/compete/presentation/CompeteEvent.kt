package com.typer.typerush.compete.presentation

import com.typer.typerush.compete.domain.models.ContestCardModel

interface CompeteEvent {
    data class NewParticipantJoined(val contestId: String) : CompeteEvent
    data class GetContestCards(val contestCards: List<ContestCardModel>) : CompeteEvent
    data class CreatedContestCard(val contestCardModel: ContestCardModel) : CompeteEvent
    data class Error(val message: String) : CompeteEvent
    object JoinedContest : CompeteEvent
    object GotContestInfoFromCode : CompeteEvent
    data class JoinedContestCard(val contestCardModel: ContestCardModel) : CompeteEvent
}