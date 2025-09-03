package com.typer.typerush.compete.data.handler

import android.util.Log
import com.typer.typerush.compete.domain.models.ContestCardModel
import com.typer.typerush.core.websocket.SuccessResponse
import com.typer.typerush.compete.presentation.CompeteEvent
import com.typer.typerush.core.CurrentContest
import com.typer.typerush.core.either.Either
import com.typer.typerush.core.failure.SocketFailure
import com.typer.typerush.core.websocket.EventBus
import com.typer.typerush.core.websocket.WebSocketEvent
import com.typer.typerush.core.websocket.WebSocketHandler
import com.typer.typerush.create_contest.domain.models.ContestModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.encodeToJsonElement

@Serializable
data class ContestIdAfterJoining(
    val contestId: String
)

class CompeteHandler(
    private val eventBus: EventBus,
    private val currentContest: CurrentContest
): WebSocketHandler {
    private val _events = MutableSharedFlow<CompeteEvent>(
        replay = 1,
        extraBufferCapacity = 64
    )
    val events: SharedFlow<CompeteEvent> = _events.asSharedFlow()

    private val json = Json { ignoreUnknownKeys = true }

    override fun supportedTypes(): List<String> {
        return listOf("getContests", "newParticipantJoined", "updatingAfterCreated", "updatingAfterJoined", "joiningContestWithCode", "contestInfoFromCode", "error")
    }

    override suspend fun handle(type: String, message: JsonElement?) {
        try {
            val res = json.decodeFromJsonElement<SuccessResponse>(message!!)
            if (res.message != "Success") {
                _events.emit(CompeteEvent.Error(res.message))
                return
            }
            if (res.response == null) {
                _events.emit(CompeteEvent.Error("Response is null"))
                return
            }
            when (type) {
                "getContests" -> {
                    val contestCards = json.decodeFromJsonElement<List<ContestCardModel>>(res.response)
                    _events.emit(CompeteEvent.GetContestCards(contestCards))
                }
                "newParticipantJoined" -> {
                    val contestId = json.decodeFromJsonElement<ContestIdAfterJoining>(res.response).contestId
                    _events.emit(CompeteEvent.NewParticipantJoined(contestId))
                }
                "updatingAfterJoined" -> {
                    val contestCardModel = json.decodeFromJsonElement<ContestCardModel>(res.response)
                    _events.emit(CompeteEvent.JoinedContestCard(contestCardModel))
                }
                "updatingAfterCreated" -> {
                    val contestCardModel = json.decodeFromJsonElement<ContestCardModel>(res.response)
                    _events.emit(CompeteEvent.CreatedContestCard(contestCardModel))
                }
                "joiningContestWithCode" -> {
                    val contestModel = json.decodeFromJsonElement<ContestModel>(res.response)
                    currentContest.setCurrentContest(contestModel)
                    _events.emit(CompeteEvent.JoinedContest)
                }
                "contestInfoFromCode" -> {
                    val contestModel = json.decodeFromJsonElement<ContestModel>(res.response)
                    currentContest.setCurrentContest(contestModel)
                    _events.emit(CompeteEvent.GotContestInfoFromCode)
                }
                "error" -> {
                    _events.emit(CompeteEvent.Error(res.response.toString()))
                }
                else -> {
                    _events.emit(CompeteEvent.Error("Unknown message type: $type"))
                }
            }
        } catch (e: Exception) {
            _events.emit(CompeteEvent.Error("Error handling message: ${e.message}"))
        }
    }

    suspend fun getContests(): Either<SocketFailure, Unit> {
        return try {
            eventBus.publish(WebSocketEvent.SendMessage("getContests", null))
            Either.Right(Unit)
        } catch (e: Exception) {
            _events.emit(CompeteEvent.Error("Can't get contests: ${e.message}"))
            Either.Left(SocketFailure("Can't get contests: ${e.message}"))
        }
    }

    suspend fun joinContestByCode(code: String): Either<SocketFailure, Unit> {
        return try {
            eventBus.publish(WebSocketEvent.SendMessage("joiningContestWithCode", json.encodeToJsonElement(mapOf("code" to code))))
            Either.Right(Unit)
        } catch (e: Exception) {
            _events.emit(CompeteEvent.Error("Can't join contest: ${e.message}"))
            Either.Left(SocketFailure("Can't join contest: ${e.message}"))
        }
    }

    suspend fun getContestInfoByCode(code: String): Either<SocketFailure, Unit> {
        return try {
            eventBus.publish(WebSocketEvent.SendMessage("contestInfoFromCode", json.encodeToJsonElement(mapOf("code" to code))))
            Either.Right(Unit)
            } catch (e: Exception) {
            _events.emit(CompeteEvent.Error("Can't get contest info: ${e.message}"))
            Either.Left(SocketFailure("Can't get contest info: ${e.message}"))
        }
    }

}