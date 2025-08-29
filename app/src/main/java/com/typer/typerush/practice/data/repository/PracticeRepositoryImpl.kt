package com.typer.typerush.practice.data.repository

import com.typer.typerush.core.either.Either
import com.typer.typerush.core.failure.NetworkFailure
import com.typer.typerush.practice.data.datasource.PracticeRemoteDatasource
import com.typer.typerush.practice.domain.models.PracticeCardModel
import com.typer.typerush.practice.domain.repository.PracticeRepository

class PracticeRepositoryImpl(
    private val remoteDataSource: PracticeRemoteDatasource
): PracticeRepository {
    override suspend fun getPracticeCards(): Either<NetworkFailure, List<PracticeCardModel>> {
        return remoteDataSource.getTypeItems()
    }
}