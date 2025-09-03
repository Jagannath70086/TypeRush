package com.typer.typerush.typetest.data.repository

import com.typer.typerush.core.either.Either
import com.typer.typerush.core.failure.Failure
import com.typer.typerush.core.failure.UnexpectedFailure
import com.typer.typerush.core.websocket.WebSocketEvent
import com.typer.typerush.core.websocket.WebSocketService
import com.typer.typerush.typetest.data.datasource.TypeTestRemoteDatasource
import com.typer.typerush.typetest.data.handlers.TypeTestHandlers
import com.typer.typerush.typetest.domain.models.SubmissionModel
import com.typer.typerush.typetest.domain.repository.TypeTestRepository
import com.typer.typerush.typetest.presentation.TypeTestEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow

class TypeTestRepositoryImpl(
    private val socketService: WebSocketService,
    private val handlers: TypeTestHandlers,
    private val remoteDatasource: TypeTestRemoteDatasource
): TypeTestRepository {
    override fun getTypeTestEvents(): SharedFlow<TypeTestEvent> = handlers.events

    override fun getSocketEvents(): Flow<WebSocketEvent> = socketService.events

    override suspend fun typeTestFinished(
        type: String,
        submissionModel: SubmissionModel
    ): Either<Failure, String> {
        try {
            if (type == "practice") {
                return remoteDatasource.submitTypeTest(submissionModel)
            }
            else if (type == "contest") {
                return handlers.contestFinished(submissionModel)
            }
            return Either.Left(UnexpectedFailure("Unknown type"))
        } catch (e: Exception) {
            return Either.Left(UnexpectedFailure(e.message.toString()))
        }
    }
}