package com.typer.typerush.typetest.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class SubmissionModel(
    val contestId: String,
    val userId: String,
    val wpm: Int,
    val accuracy: Double,
)
