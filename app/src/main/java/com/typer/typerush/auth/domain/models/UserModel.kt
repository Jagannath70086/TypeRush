package com.typer.typerush.auth.domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserModel(
    @SerialName("_id")
    val id: String? = null,
    val name: String = "Anonymous",
    val email: String = "abc@example.com",
    val photoUrl: String = "",
)