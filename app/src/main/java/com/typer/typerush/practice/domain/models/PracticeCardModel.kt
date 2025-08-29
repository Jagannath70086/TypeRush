package com.typer.typerush.practice.domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PracticeCardModel(
    @SerialName("_id")
    val id: String,
    val title: String,
    val tags: List<String>,
    val time: Int
)