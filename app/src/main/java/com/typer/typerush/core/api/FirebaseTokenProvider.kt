package com.typer.typerush.core.api

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

class FirebaseTokenProvider{
    private val firebaseAuth = FirebaseAuth.getInstance()

    suspend fun getToken(): String? {
        return try {
            val currentUser = firebaseAuth.currentUser
            currentUser?.getIdToken(true)?.await()?.token
        } catch (e: Exception) {
            null
        }
    }
}