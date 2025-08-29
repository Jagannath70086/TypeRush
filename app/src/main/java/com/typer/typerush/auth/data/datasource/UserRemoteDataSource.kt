package com.typer.typerush.auth.data.datasource

import com.typer.typerush.auth.domain.models.UserModel
import com.typer.typerush.core.api.getUrl
import com.typer.typerush.core.api.safeCall
import com.typer.typerush.core.either.Either
import com.typer.typerush.core.failure.NetworkFailure
import io.ktor.client.HttpClient
import io.ktor.client.request.get

class UserRemoteDataSource(
    private val client: HttpClient
) {
    suspend fun getUser(): Either<NetworkFailure, UserModel> {
        return safeCall {
            client.get(getUrl("auth/me"))
        }
    }
}
