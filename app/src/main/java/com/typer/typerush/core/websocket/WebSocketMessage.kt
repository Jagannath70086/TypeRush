package com.typer.typerush.core.websocket

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class WebSocketMessage(
    val type: String,
    val data: JsonElement? = null
)