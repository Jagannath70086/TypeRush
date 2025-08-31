package com.typer.typerush.compete.domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ContestCardModel(
    @SerialName("_id")
    val id: String,
    val title: String,
    val authorName: String,
    val contestCode: String,
    val status: String,
    val tags: List<String>,
    val time: Int,
    val currentPlayers: Int,
    val totalPlayers: Int,
    val isMine: Boolean
)