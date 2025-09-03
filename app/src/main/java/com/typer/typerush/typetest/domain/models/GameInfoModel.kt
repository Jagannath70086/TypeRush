package com.typer.typerush.typetest.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class GameInfoModel(
    val id: String? = null,
    val text: String,
    val time: Long,
    val result: SubmissionModel? = null,
)
