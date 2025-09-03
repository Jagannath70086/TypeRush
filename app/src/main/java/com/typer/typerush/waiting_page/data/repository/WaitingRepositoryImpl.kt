package com.typer.typerush.waiting_page.data.repository

import com.typer.typerush.core.either.Either
import com.typer.typerush.core.failure.SocketFailure
import com.typer.typerush.core.websocket.WebSocketEvent
import com.typer.typerush.core.websocket.WebSocketService
import com.typer.typerush.waiting_page.data.handlers.WaitingHandler
import com.typer.typerush.waiting_page.domain.repository.WaitingRepository
import com.typer.typerush.waiting_page.presentation.WaitingEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow

class WaitingRepositoryImpl(
    private val waitingHandler: WaitingHandler,
    private val socketService: WebSocketService
): WaitingRepository {
    override fun getWaitingEvents(): SharedFlow<WaitingEvent> = waitingHandler.events

    override fun getSocketEvents(): Flow<WebSocketEvent> = socketService.events
    override suspend fun startRoom(): Either<SocketFailure, Unit> = waitingHandler.startContest()
}