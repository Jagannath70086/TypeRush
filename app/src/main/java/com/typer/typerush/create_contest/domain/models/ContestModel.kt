package com.typer.typerush.create_contest.domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ContestModel(
    @SerialName("_id")
    val id: String? = null,
    val title: String = "",
    val contestCode: String? = null,
    val textSnippet: String = "",
    val duration: Int = 1,
    val remainingTime: Long? = null,
    val maxPlayers: Int = 100,
    val status: String = "waiting",
    val tags: List<String> = emptyList(),
    val difficulty: String = "medium",
    val players: List<ParticipantModel> = mutableListOf(),
    val startTime: Long? = null,
    val endTime: Long? = null
)
