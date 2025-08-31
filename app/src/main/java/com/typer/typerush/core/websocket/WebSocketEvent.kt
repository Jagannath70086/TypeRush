package com.typer.typerush.core.websocket

import com.typer.typerush.compete.domain.models.ContestCardModel
import kotlinx.serialization.json.JsonElement

interface WebSocketEvent {
    data object Connected: WebSocketEvent
    data object Disconnected: WebSocketEvent
    data class SendMessage(val type: String, val data: JsonElement?): WebSocketEvent
    data class Error(val message: String): WebSocketEvent
}