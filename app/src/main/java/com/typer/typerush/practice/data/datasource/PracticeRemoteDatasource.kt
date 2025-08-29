package com.typer.typerush.practice.data.datasource

import com.typer.typerush.core.api.getUrl
import com.typer.typerush.core.api.safeCall
import com.typer.typerush.core.either.Either
import com.typer.typerush.core.failure.NetworkFailure
import com.typer.typerush.practice.domain.models.PracticeCardModel
import io.ktor.client.HttpClient
import io.ktor.client.request.get

class PracticeRemoteDatasource(
    private val client: HttpClient
) {
    suspend fun getTypeItems(): Either<NetworkFailure, List<PracticeCardModel>> {
        return safeCall {
            client.get(getUrl("/type-items"))
        }
    }
}