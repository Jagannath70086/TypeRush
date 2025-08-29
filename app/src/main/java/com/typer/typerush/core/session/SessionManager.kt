package com.typer.typerush.core.session

import com.typer.typerush.auth.domain.models.UserModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SessionManager {
    private val _currentUser = MutableStateFlow<UserModel?>(null)
    val currentUser: StateFlow<UserModel?> = _currentUser.asStateFlow()

    fun setUser(user: UserModel?) {
        _currentUser.value = user
    }
}
