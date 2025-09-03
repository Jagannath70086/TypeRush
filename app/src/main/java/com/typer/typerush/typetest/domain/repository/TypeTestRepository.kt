package com.typer.typerush.typetest.domain.repository

import com.typer.typerush.core.either.Either
import com.typer.typerush.core.failure.Failure
import com.typer.typerush.core.websocket.WebSocketEvent
import com.typer.typerush.typetest.domain.models.SubmissionModel
import com.typer.typerush.typetest.presentation.TypeTestEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow

interface TypeTestRepository {
    fun getTypeTestEvents(): SharedFlow<TypeTestEvent>
    fun getSocketEvents(): Flow<WebSocketEvent>
    suspend fun typeTestFinished(type: String, submissionModel: SubmissionModel): Either<Failure, String>
}