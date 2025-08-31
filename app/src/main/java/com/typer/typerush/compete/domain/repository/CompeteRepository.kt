package com.typer.typerush.compete.domain.repository

import com.typer.typerush.compete.presentation.CompeteEvent
import com.typer.typerush.core.either.Either
import com.typer.typerush.core.failure.SocketFailure
import com.typer.typerush.core.websocket.WebSocketEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow

interface CompeteRepository {
    fun getCompeteEvents(): SharedFlow<CompeteEvent>
    fun getSocketEvents(): Flow<WebSocketEvent>
    suspend fun connect(): Either<SocketFailure, Unit>
    fun disconnect()
    suspend fun getContests(): Either<SocketFailure, Unit>
    suspend fun joinByContestCode(contestCode: String): Either<SocketFailure, Unit>
    suspend fun getContestInfoAndEnterWaitingPage(contestCode: String): Either<SocketFailure, Unit>
}