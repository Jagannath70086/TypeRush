package com.typer.typerush.typetest.data.datasource

import com.typer.typerush.core.api.getUrl
import com.typer.typerush.core.api.safeCall
import com.typer.typerush.core.either.Either
import com.typer.typerush.core.failure.NetworkFailure
import com.typer.typerush.typetest.domain.models.SubmissionModel
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody

class TypeTestRemoteDatasource(
    private val client: HttpClient
) {
    suspend fun submitTypeTest(submissionModel: SubmissionModel): Either<NetworkFailure, String> {
        return safeCall {
            client.post(getUrl("/type-items/submit")) {
                setBody(submissionModel)
            }
        }
    }
}