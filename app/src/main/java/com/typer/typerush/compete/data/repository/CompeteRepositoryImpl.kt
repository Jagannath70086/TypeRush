package com.typer.typerush.compete.data.repository

import com.typer.typerush.compete.data.handler.CompeteHandler
import com.typer.typerush.compete.domain.repository.CompeteRepository
import com.typer.typerush.compete.presentation.CompeteEvent
import com.typer.typerush.core.either.Either
import com.typer.typerush.core.failure.SocketFailure
import com.typer.typerush.core.websocket.WebSocketEvent
import com.typer.typerush.core.websocket.WebSocketService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow

class CompeteRepositoryImpl(
    private val competeHandler: CompeteHandler,
    private val socketService: WebSocketService
): CompeteRepository {
    override fun getCompeteEvents(): SharedFlow<CompeteEvent> {
        return competeHandler.events
    }

    override fun getSocketEvents(): Flow<WebSocketEvent> {
        return socketService.events
    }

    override suspend fun connect(): Either<SocketFailure, Unit> = socketService.connect()

    override fun disconnect() = socketService.disconnect()

    override suspend fun getContests(): Either<SocketFailure, Unit> = competeHandler.getContests()
    override suspend fun joinByContestCode(contestCode: String): Either<SocketFailure, Unit> = competeHandler.joinContestByCode(contestCode)

    override suspend fun getContestInfoAndEnterWaitingPage(contestCode: String): Either<SocketFailure, Unit> = competeHandler.getContestInfoByCode(contestCode)
}