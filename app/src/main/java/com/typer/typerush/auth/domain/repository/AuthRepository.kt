package com.typer.typerush.auth.domain.repository

import com.typer.typerush.auth.domain.models.UserModel
import com.typer.typerush.core.either.Either
import com.typer.typerush.core.failure.Failure

interface AuthRepository {
    suspend fun signInWithGoogle(): Either<Failure, UserModel>
    suspend fun signOut(): Either<Failure, Unit>
}