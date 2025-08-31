package com.typer.typerush.create_contest.data.handler

import android.util.Log
import com.typer.typerush.compete.domain.models.ContestCardModel
import com.typer.typerush.compete.presentation.CompeteEvent
import com.typer.typerush.core.either.Either
import com.typer.typerush.core.failure.SocketFailure
import com.typer.typerush.core.websocket.EventBus
import com.typer.typerush.core.websocket.SuccessResponse
import com.typer.typerush.core.websocket.WebSocketEvent
import com.typer.typerush.core.websocket.WebSocketHandler
import com.typer.typerush.create_contest.domain.models.ContestModel
import com.typer.typerush.create_contest.presentation.CreateContestEvent
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.decodeFromJsonElement

class CreateContestHandler(
    private val eventBus: EventBus
): WebSocketHandler {
    private val _events = MutableSharedFlow<CreateContestEvent>(
    replay = 1,
    extraBufferCapacity = 64
)
    val events: SharedFlow<CreateContestEvent> = _events.asSharedFlow()

    private val json = Json {
        ignoreUnknownKeys = true
        encodeDefaults = true
    }

    override fun supportedTypes(): List<String> {
        return listOf("createdContest")
    }

    override suspend fun handle(type: String, message: JsonElement?) {
        try {
            val res = json.decodeFromJsonElement<SuccessResponse>(message!!)
            if (res.message != "Success") {
                _events.emit(CreateContestEvent.Error(res.message))
                return
            }
            if (res.response == null) {
                _events.emit(CreateContestEvent.Error("Nothing's found"))
                return
            }
            when (type) {
                "createdContest" -> {
                    val createdContest = json.decodeFromJsonElement<ContestModel>(res.response)
                    _events.emit(CreateContestEvent.CreatedContest(createdContest))
                }
                else -> {
                    _events.emit(CreateContestEvent.Error("Unknown message type: $type"))
                }
            }
        } catch (e: Exception) {
            _events.emit(CreateContestEvent.Error("Error handling message: ${e.message}"))
        }
    }

    suspend fun createContest(contestModel: ContestModel): Either<SocketFailure, Unit> {
        return try {
            val test = WebSocketEvent.SendMessage("createContest", json.encodeToJsonElement(
                ContestModel.serializer(),contestModel))
            Log.d("testhmm", test.toString())
            eventBus.publish(test)
            Either.Right(Unit)
        } catch (e: Exception) {
            _events.emit(CreateContestEvent.Error("Can't get contests: ${e.message}"))
            Either.Left(SocketFailure("Can't get contests: ${e.message}"))
        }
    }

}