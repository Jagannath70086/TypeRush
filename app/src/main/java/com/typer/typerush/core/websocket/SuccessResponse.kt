package com.typer.typerush.core.websocket

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class SuccessResponse(
    val message: String,
    val response: JsonElement? = null
)