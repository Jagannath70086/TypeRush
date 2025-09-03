package com.typer.typerush.waiting_page.data.handlers

import com.typer.typerush.compete.presentation.CompeteEvent
import com.typer.typerush.core.CurrentContest
import com.typer.typerush.core.CurrentGameProvider
import com.typer.typerush.core.either.Either
import com.typer.typerush.core.failure.SocketFailure
import com.typer.typerush.core.websocket.EventBus
import com.typer.typerush.core.websocket.SuccessResponse
import com.typer.typerush.core.websocket.WebSocketEvent
import com.typer.typerush.core.websocket.WebSocketHandler
import com.typer.typerush.create_contest.domain.models.ParticipantModel
import com.typer.typerush.typetest.domain.models.GameInfoModel
import com.typer.typerush.waiting_page.presentation.WaitingEvent
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.encodeToJsonElement

class WaitingHandler(
    private val eventBus: EventBus,
    private val currentContest: CurrentContest,
    private val currentGameProvider: CurrentGameProvider
): WebSocketHandler {

    private val _events = MutableSharedFlow<WaitingEvent>(
        replay = 1,
        extraBufferCapacity = 64
    )
    val events: SharedFlow<WaitingEvent> = _events.asSharedFlow()

    private val json = Json { ignoreUnknownKeys = true }

    override fun supportedTypes(): List<String> = listOf("newParticipantJoinedWaiting", "contestStartSuccess", "userFinishedContest")

    override suspend fun handle(
        type: String,
        message: JsonElement?
    ) {
        try {
            val res = json.decodeFromJsonElement<SuccessResponse>(message!!)
            if (res.message != "Success") {
                _events.emit(WaitingEvent.Error(res.message))
                return
            }
            when (type) {
                "newParticipantJoinedWaiting" -> {
                    val newParticipant = json.decodeFromJsonElement(ParticipantModel.serializer(), res.response!!)
                    currentContest.setCurrentContest(currentContest.currentContest.value?.copy(
                        players = currentContest.currentContest.value!!.players.plus(newParticipant)
                    ))
                    _events.emit(WaitingEvent.NewParticipantJoined)
                }
                "contestStartSuccess" -> {
                    val currentGame = json.decodeFromJsonElement<GameInfoModel>(res.response!!)
                    currentGameProvider.setCurrentGame(currentGame)
                    currentContest.setCurrentContest(currentContest.currentContest.value?.copy(
                        status = "Started"
                    ))
                    _events.emit(WaitingEvent.ContestStarted)
                }
                "userFinishedContest" -> {
                    val newDetails = json.decodeFromJsonElement(ParticipantModel.serializer(), res.response!!)
                    currentContest.setCurrentContest(currentContest.currentContest.value?.copy(
                        players = currentContest.currentContest.value!!.players.map { player ->
                            if (player.userId == newDetails.userId) {
                                newDetails
                            } else {
                                player
                            }
                        }
                    ))
                    _events.emit(WaitingEvent.UserFinishedContest)
                }
                else -> {
                    _events.emit(WaitingEvent.Error("Unknown message type: $type"))
                }
            }
        } catch (e: Exception) {
            _events.emit(WaitingEvent.Error("Some error occurred ${e.message}"))
        }
    }

    suspend fun startContest(): Either<SocketFailure, Unit> {
        return try {
            val currentContestId = currentContest.currentContest.value!!.id!!
            eventBus.publish(WebSocketEvent.SendMessage(
                type = "startRoomById",
                data = json.encodeToJsonElement(mapOf("contestId" to currentContestId))
            ))
            Either.Right(Unit)
        } catch (e: Exception) {
            _events.emit(WaitingEvent.Error("Couldn't start room"))
            Either.Left(SocketFailure("Some error occurred"))
        }
    }
}