package com.typer.typerush.core.websocket

import com.typer.typerush.BuildConfig
import com.typer.typerush.core.either.Either
import com.typer.typerush.core.failure.SocketFailure
import io.ktor.client.HttpClient
import io.ktor.client.plugins.websocket.DefaultClientWebSocketSession
import io.ktor.client.plugins.websocket.webSocket
import io.ktor.websocket.Frame
import io.ktor.websocket.readText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement

class WebSocketService(
    private val client: HttpClient,
    handlers: List<WebSocketHandler>,
    private val eventBus: EventBus
) {
    private val _events = Channel<WebSocketEvent>(Channel.UNLIMITED)
    val events: Flow<WebSocketEvent> = _events.receiveAsFlow()

    private val handlerRegistry: Map<String, WebSocketHandler> =
        handlers.flatMap { it.supportedTypes().map { type -> type to it } }.toMap()

    private val json = Json { ignoreUnknownKeys = true }
    private var webSocketSession: DefaultClientWebSocketSession? = null
    private var connectionJob: Job? = null

    init {
        CoroutineScope(Dispatchers.Default).launch {
            eventBus.events.collect { event ->
                when (event) {
                    is WebSocketEvent.SendMessage -> sendMessage(event.type, event.data)
                    else -> Unit
                }
            }
        }
    }

    fun connect(): Either<SocketFailure, Unit> {
        return try {
            connectionJob = CoroutineScope(Dispatchers.IO).launch {
                client.webSocket(
                    urlString = BuildConfig.BASE_SOCKET_URL
                ) {
                    webSocketSession = this
                    _events.send(WebSocketEvent.Connected)
                    try {
                        for (frame in incoming) {
                            when (frame) {
                                is Frame.Text -> {
                                    val messageText = frame.readText()
                                    val message = json.decodeFromString<WebSocketMessage>(messageText)
                                    val handler = handlerRegistry[message.type]

                                    if (handler != null) {
                                        handler.handle(message.type, message.data)
                                    } else {
                                        _events.send(WebSocketEvent.Error( "Unknown message type: ${message.type}"))
                                    }
                                }
                                else -> {}
                            }
                        }
                    } catch (e: Exception) {
                        _events.send(WebSocketEvent.Error("Connection Error: ${e.message}"))
                    } finally {
                        webSocketSession = null
                        _events.send(WebSocketEvent.Disconnected)
                    }
                }
            }
            Either.Right(Unit)
        } catch (e: Exception) {
            Either.Left(SocketFailure("Connection Error: ${e.message}"))
        }
    }

    fun disconnect() {
        connectionJob?.cancel()
        webSocketSession = null
    }

    private suspend fun sendMessage(type: String, data: JsonElement? = null) {
        if (webSocketSession == null) {
            _events.send(WebSocketEvent.Error("Not connected to server"))
            return
        }
        webSocketSession!!.let { session ->
            try {
                val message = WebSocketMessage(type, data)
                session.send(Frame.Text(json.encodeToString( message)))
            } catch (e: Exception) {
                _events.send(WebSocketEvent.Error("Error sending message: ${e.message}"))
            }
        }
    }
}