package com.typer.typerush.core.api

import com.typer.typerush.core.either.Either
import com.typer.typerush.core.failure.NetworkError
import com.typer.typerush.core.failure.NetworkFailure
import io.ktor.client.statement.HttpResponse
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.coroutines.ensureActive
import kotlinx.serialization.SerializationException
import kotlin.coroutines.coroutineContext

suspend inline fun <reified B> safeCall(
    execute: () -> HttpResponse
): Either<NetworkFailure, B> {
    val response = try {
        execute()
    } catch (e: UnresolvedAddressException) {
        return Either.Left(NetworkFailure(NetworkError.NO_INTERNET_CONNECTION))
    } catch (e: SerializationException) {
        return Either.Left(NetworkFailure(NetworkError.SERIALIZATION))
    } catch (e: Exception) {
        coroutineContext.ensureActive()
        return Either.Left(NetworkFailure(NetworkError.UNKNOWN))
    }

    return responseToResult(response)
}