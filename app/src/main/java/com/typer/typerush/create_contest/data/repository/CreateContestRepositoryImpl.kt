package com.typer.typerush.create_contest.data.repository

import com.typer.typerush.core.either.Either
import com.typer.typerush.core.failure.Failure
import com.typer.typerush.core.failure.SocketFailure
import com.typer.typerush.core.websocket.WebSocketEvent
import com.typer.typerush.core.websocket.WebSocketService
import com.typer.typerush.create_contest.data.handler.CreateContestHandler
import com.typer.typerush.create_contest.domain.models.ContestModel
import com.typer.typerush.create_contest.domain.repository.CreateContestRepository
import com.typer.typerush.create_contest.presentation.CreateContestEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow

class CreateContestRepositoryImpl(
    private val createContestHandler: CreateContestHandler,
    private val socketService: WebSocketService
): CreateContestRepository {
    override fun getCreateContestEvents(): SharedFlow<CreateContestEvent> {
        return createContestHandler.events
    }

    override fun getSocketEvents(): Flow<WebSocketEvent> {
        return socketService.events
    }

    override suspend fun connect(): Either<SocketFailure, Unit> = socketService.connect()

    override suspend fun createContest(contest: ContestModel): Either<Failure, Unit> = createContestHandler.createContest(contest)
}