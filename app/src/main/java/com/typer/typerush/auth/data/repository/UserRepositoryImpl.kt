package com.typer.typerush.auth.data.repository

import com.typer.typerush.auth.data.datasource.UserRemoteDataSource
import com.typer.typerush.auth.domain.models.UserModel
import com.typer.typerush.auth.domain.repository.UserRepository
import com.typer.typerush.core.either.Either
import com.typer.typerush.core.failure.Failure
import com.typer.typerush.core.failure.ServerFailure
import com.typer.typerush.core.session.SessionManager

class UserRepositoryImpl(
    private val userRemoteDataSource: UserRemoteDataSource,
    private val sessionManager: SessionManager
): UserRepository {
    override suspend fun getUser(): Either<Failure, UserModel> {
        return try {
            val user = userRemoteDataSource.getUser()
            if (user is Either.Right) {
                sessionManager.setUser(user.value)
                Either.Right(user.value)
            } else {
                Either.Left(ServerFailure("Error occurred at server"))
            }
        } catch (e: Exception) {
            Either.Left(ServerFailure("Failed to fetch user"))
        }
    }
}