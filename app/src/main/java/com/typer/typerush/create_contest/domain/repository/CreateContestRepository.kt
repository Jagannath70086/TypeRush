package com.typer.typerush.create_contest.domain.repository

import com.typer.typerush.core.either.Either
import com.typer.typerush.core.failure.Failure
import com.typer.typerush.core.failure.SocketFailure
import com.typer.typerush.core.websocket.WebSocketEvent
import com.typer.typerush.create_contest.domain.models.ContestModel
import com.typer.typerush.create_contest.presentation.CreateContestEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow

interface CreateContestRepository {
    fun getCreateContestEvents(): SharedFlow<CreateContestEvent>
    fun getSocketEvents(): Flow<WebSocketEvent>
    suspend fun connect(): Either<SocketFailure, Unit>
    suspend fun createContest(contest: ContestModel): Either<Failure, Unit>
}