package com.typer.typerush.create_contest.presentation

import com.typer.typerush.create_contest.domain.models.ContestModel

interface CreateContestEvent {
    data class CreatedContest(val contestModel: ContestModel) : CreateContestEvent
    data class Error(val message: String) : CreateContestEvent
}