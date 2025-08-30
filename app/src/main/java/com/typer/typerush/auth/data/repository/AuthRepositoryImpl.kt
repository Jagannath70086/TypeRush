package com.typer.typerush.auth.data.repository

import android.content.Context
import androidx.credentials.ClearCredentialStateRequest
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import com.typer.typerush.R
import com.typer.typerush.auth.data.datasource.AuthRemoteDataSource
import com.typer.typerush.auth.domain.models.UserModel
import com.typer.typerush.auth.domain.repository.AuthRepository
import com.typer.typerush.core.either.Either
import com.typer.typerush.core.failure.AuthFailure
import com.typer.typerush.core.failure.Failure
import com.typer.typerush.core.session.SessionManager
import kotlinx.coroutines.tasks.await

class AuthRepositoryImpl(
    private val context: Context,
    private val authRemoteDataSource: AuthRemoteDataSource,
    private val sessionManager: SessionManager
): AuthRepository {
    private val auth = Firebase.auth
    private val credentialManager: CredentialManager = CredentialManager.create(context)

    override suspend fun signInWithGoogle(): Either<Failure, UserModel> {
        try {
            val credentialResponse = getGoogleCredential()
            val credential = credentialResponse.credential

            if (credential !is CustomCredential || credential.type != GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                return Either.Left(AuthFailure("Invalid Credential"))
            }

            val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
            val idToken = googleIdTokenCredential.idToken

            val firebaseResult = firebaseAuthWithGoogle(idToken)

            if (firebaseResult !is Either.Right) {
                return Either.Left(AuthFailure("Firebase Authentication Failed"))
            }

            val apiUser = authRemoteDataSource.loginWithGoogle()

            if (apiUser !is Either.Right) {
                return Either.Left(AuthFailure("API Authentication Failed"))
            }

            sessionManager.setUser(apiUser.value)
            return Either.Right(apiUser.value)
        } catch (e: Exception) {
            return Either.Left(AuthFailure("SignIn Failed"))
        }
    }

    override suspend fun signOut(): Either<Failure, Unit> {
        return try {
            auth.signOut()

            val clearRequest = ClearCredentialStateRequest()
            credentialManager.clearCredentialState(clearRequest)


            sessionManager.setUser(null)
            Either.Right(Unit)
        } catch (e: Exception) {
            Either.Left(AuthFailure("SignOut Failed"))
        }
    }

    private suspend fun getGoogleCredential(): GetCredentialResponse {
        val googleIdOption = GetGoogleIdOption.Builder()
            .setServerClientId(context.getString(R.string.web_client_id))
            .setFilterByAuthorizedAccounts(false)
            .build()

        val request = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()

        return credentialManager.getCredential(
            context = context,
            request = request
        )
    }

    private suspend fun firebaseAuthWithGoogle(idToken: String): Either<Failure, FirebaseUser> {
        return try {
            val credential = GoogleAuthProvider.getCredential(idToken, null)
            val result = auth.signInWithCredential(credential).await()

            result.user?.let { user ->
                Either.Right(user)
            } ?: run {
                Either.Left(AuthFailure("Firebase Authentication Failed"))
            }
        } catch (e: Exception) {
            Either.Left(AuthFailure("Firebase Authentication Failed"))
        }
    }
}