package com.typer.typerush.core.websocket

import kotlinx.serialization.json.JsonElement

interface WebSocketHandler {
    fun supportedTypes(): List<String>
    suspend fun handle(type: String, message: JsonElement?)
}