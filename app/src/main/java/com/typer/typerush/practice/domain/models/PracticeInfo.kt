package com.typer.typerush.practice.domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PracticeInfo(
    @SerialName("_id")
    val id: String,
    val text: String,
    val time: Long,
)