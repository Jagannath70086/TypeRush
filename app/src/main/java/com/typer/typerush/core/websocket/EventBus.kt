package com.typer.typerush.core.websocket

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

class EventBus {
    private val _events = MutableSharedFlow<WebSocketEvent>(extraBufferCapacity = 100)
    val events: SharedFlow<WebSocketEvent> = _events

    suspend fun publish(event: WebSocketEvent) {
        _events.emit(event)
    }
}