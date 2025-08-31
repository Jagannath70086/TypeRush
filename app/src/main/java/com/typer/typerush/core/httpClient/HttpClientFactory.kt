package com.typer.typerush.core.httpClient

import com.typer.typerush.core.api.FirebaseTokenProvider
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.ANDROID
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json

object HttpClientFactory {
    fun create(engine: HttpClientEngine): HttpClient {
        return HttpClient(engine) {
            install(Logging) {
                logger = Logger.ANDROID
                level = LogLevel.ALL
            }
            install(ContentNegotiation) {
                json(
                    json = Json {
                        ignoreUnknownKeys = true
                    }
                )
            }
            defaultRequest {
                contentType(ContentType.Application.Json)
            }
        }
    }

    fun createWithToken(engine: HttpClientEngine, tokenProvider: FirebaseTokenProvider): HttpClient {
        return HttpClient(engine) {
            install(Logging) {
                logger = Logger.ANDROID
                level = LogLevel.ALL
            }
            install(ContentNegotiation) {
                json(
                    json = Json {
                        ignoreUnknownKeys = true
                    }
                )
            }
            defaultRequest {
                contentType(ContentType.Application.Json)
                val token = runBlocking { tokenProvider.getToken() }
                if (token != null) {
                    header("Authorization", "Bearer $token")
                }
            }
        }
    }

    fun createWithWebSocket(tokenProvider: FirebaseTokenProvider): HttpClient {
        return HttpClient {
            install(WebSockets)
            install(ContentNegotiation) {
                json(
                    json = Json {
                        ignoreUnknownKeys = true
                    }
                )
            }
            install(Logging) {
                level = LogLevel.INFO
            }
            defaultRequest {
                contentType(ContentType.Application.Json)
                val token = runBlocking { tokenProvider.getToken() }
                if (token != null) {
                    header("Authorization", "Bearer $token")
                }
            }
        }
    }
}