package com.typer.typerush.typetest.data.handlers

import com.typer.typerush.core.either.Either
import com.typer.typerush.core.failure.SocketFailure
import com.typer.typerush.core.websocket.EventBus
import com.typer.typerush.core.websocket.SuccessResponse
import com.typer.typerush.core.websocket.WebSocketEvent
import com.typer.typerush.core.websocket.WebSocketHandler
import com.typer.typerush.typetest.domain.models.SubmissionModel
import com.typer.typerush.typetest.presentation.TypeTestEvent
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.encodeToJsonElement

class TypeTestHandlers(
    private val eventBus: EventBus
): WebSocketHandler {

    private val _events = MutableSharedFlow<TypeTestEvent>(
        replay = 1,
        extraBufferCapacity = 64
    )
    val events: SharedFlow<TypeTestEvent> = _events.asSharedFlow()

    private val json = Json { ignoreUnknownKeys = true }


    override fun supportedTypes(): List<String> = listOf("contestFinishedSuccess")

    override suspend fun handle(
        type: String,
        message: JsonElement?
    ) {
        try {
            val res = json.decodeFromJsonElement<SuccessResponse>(message!!)
            if (res.message != "Success") {
                _events.emit(TypeTestEvent.Error(res.message))
                return
            }
            when (type) {
                "contestFinishedSuccess" -> {
                    _events.emit(TypeTestEvent.Submitted)
                }
                else -> {
                    _events.emit(TypeTestEvent.Error("Unknown message type: $type"))
                }
            }
        } catch (e: Exception) {
            _events.emit(TypeTestEvent.Error("Some error occurred ${e.message}"))
        }
    }

    suspend fun contestFinished(submissionModel: SubmissionModel): Either<SocketFailure, String> {
        return try {
            eventBus.publish(WebSocketEvent.SendMessage(
                type = "contestFinished",
                data = json.encodeToJsonElement(submissionModel)
            ))
            Either.Right("Success")
        } catch (e: Exception) {
            _events.emit(TypeTestEvent.Error("Couldn't send message"))
            Either.Left(SocketFailure("Some error occurred"))
        }
    }
}