package com.typer.typerush.core.api

import com.typer.typerush.core.either.Either
import com.typer.typerush.core.failure.NetworkError
import com.typer.typerush.core.failure.NetworkFailure
import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse

suspend inline fun <reified T> responseToResult(
    response: HttpResponse
): Either<NetworkFailure, T> {
    return when(response.status.value) {
        in 200..299 -> {
            try {
                Either.Right(response.body<T>())
            } catch (e: NoTransformationFoundException) {
                Either.Left(NetworkFailure(NetworkError.SERIALIZATION))
            }
        }
        408 -> Either.Left(NetworkFailure(NetworkError.REQUEST_TIMEOUT))
        429 -> Either.Left(NetworkFailure(NetworkError.TOO_MANY_REQUESTS))
        in 500..599 -> Either.Left(NetworkFailure(NetworkError.SERVER_ERROR))
        else -> Either.Left(NetworkFailure(NetworkError.UNKNOWN))
    }
}