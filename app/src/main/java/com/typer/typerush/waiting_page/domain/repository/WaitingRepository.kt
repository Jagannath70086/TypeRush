package com.typer.typerush.waiting_page.domain.repository

import com.typer.typerush.core.either.Either
import com.typer.typerush.core.failure.Failure
import com.typer.typerush.core.websocket.WebSocketEvent
import com.typer.typerush.waiting_page.presentation.WaitingEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow

interface WaitingRepository {
    fun getWaitingEvents(): SharedFlow<WaitingEvent>
    fun getSocketEvents(): Flow<WebSocketEvent>
    suspend fun startRoom(): Either<Failure, Unit>
}