package com.typer.typerush.practice.domain.repository

import com.typer.typerush.core.either.Either
import com.typer.typerush.core.failure.NetworkFailure
import com.typer.typerush.practice.domain.models.PracticeCardModel

interface PracticeRepository {
    suspend fun getPracticeCards(): Either<NetworkFailure, List<PracticeCardModel>>
}