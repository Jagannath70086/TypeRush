package com.typer.typerush.compete.presentation

import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.text.toUpperCase
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.typer.typerush.compete.domain.models.ContestCardModel
import com.typer.typerush.compete.domain.repository.CompeteRepository
import com.typer.typerush.core.either.Either
import com.typer.typerush.core.session.SessionManager
import com.typer.typerush.core.websocket.WebSocketEvent
import com.typer.typerush.navigation.NavigationManager
import com.typer.typerush.navigation.Screen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CompeteViewModel(
    private val navigationManager: NavigationManager,
    private val competeRepository: CompeteRepository
): ViewModel() {
    private val _state = MutableStateFlow(CompeteState())
    val state: StateFlow<CompeteState> = _state
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _state.value
        )

    init {
        observeEvents()
        onIntent(CompeteIntent.AttemptConnection)
    }

    fun onIntent(intent: CompeteIntent) {
        when (intent) {
            CompeteIntent.AttemptConnection -> connectToSocket()
            CompeteIntent.AttemptReconnection -> connectToSocket()
            CompeteIntent.GetCompeteCards -> getCompeteCards()
            is CompeteIntent.JoinByContestCode -> joinByContestCode()
            is CompeteIntent.CreateNewContest -> {
                createNewContest()
            }
            is CompeteIntent.EnterWaitingPage -> {
                enterWaitingPage(intent.contestCode)
            }
            is CompeteIntent.EnterContestCode -> {
                _state.update { it.copy(contestCodeWritingText = intent.contestCode.toUpperCase(Locale.current)) }
            }
            CompeteIntent.DismissError -> {
                _state.update { it.copy(error = null) }
            }
            is CompeteIntent.FilterPracticeCards -> filterPracticeCards(intent.query)
            CompeteIntent.DismissSuccess -> {
                _state.update { it.copy(success = null) }
            }
        }
    }

    private fun connectToSocket() {
        _state.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            val res = competeRepository.connect()
            if (res is Either.Left) {
                _state.update { it.copy(error = res.error.message) }
            }
        }
    }

    private fun getCompeteCards() {
        viewModelScope.launch {
            competeRepository.getContests()
        }
    }

    private fun joinByContestCode() {
        if (!state.value.isConnected) {
            _state.update { it.copy(error = "Not connected to server") }
            return
        }
        if (state.value.contestCodeWritingText.isEmpty() || state.value.contestCodeWritingText.isBlank()) {
            _state.update { it.copy(error = "Please enter a contest code") }
            return
        }
        if (state.value.contestCodeWritingText.length != 6) {
            _state.update { it.copy(error = "Contest code must be 6 characters long") }
            return
        }
        viewModelScope.launch {
            competeRepository.joinByContestCode(state.value.contestCodeWritingText)
        }
    }

    private fun createNewContest() {
        if (state.value.isConnected) {
            viewModelScope.launch {
                navigationManager.navigateTo(Screen.CreateContest.route)
            }
        } else {
            _state.update { it.copy(error = "Not connected to server") }
        }
    }

    private fun enterWaitingPage(contestCode: String) {
        if (!state.value.isConnected) {
            _state.update { it.copy(error = "Not connected to server") }
            return
        }
        viewModelScope.launch {
            competeRepository.getContestInfoAndEnterWaitingPage(contestCode)
        }
    }

    private fun filterPracticeCards(query: String) {
        _state.update { it.copy(selectedFilter = query) }
        when (query) {
            "All" -> {
                _state.update { it.copy(filteredContestCards = it.contestCards) }
            }
            "My Contests" -> {
                _state.update { it.copy(
                    filteredContestCards = it.contestCards.filter { contestCard ->
                        contestCard.isMine
                    }
                ) }
            }
            else -> {
                _state.update {
                    it.copy(
                        filteredContestCards = it.contestCards.filter { contestCard ->
                            contestCard.status.toLowerCase(Locale.current) == query.toLowerCase(Locale.current)
                        }
                    )
                }
            }
        }
    }

    private fun observeEvents() {
        viewModelScope.launch {
            competeRepository.getSocketEvents().collect { event ->
                when (event) {
                    is WebSocketEvent.Error -> {
                        _state.update { it.copy(error = event.message) }
                    }
                    WebSocketEvent.Connected -> {
                        _state.update { it.copy(isConnected = true) }
                        getCompeteCards()
                    }
                    WebSocketEvent.Disconnected -> {
                        _state.update { it.copy(isConnected = false) }
                    }
                }
            }
        }

        viewModelScope.launch {
            competeRepository.getCompeteEvents().collect { event ->
                when (event) {
                    is CompeteEvent.ContestCardUpdated -> updateContestCard(event.contestCardModel)
                    is CompeteEvent.Error -> {
                        _state.update { it.copy(error = event.message, isLoading = false) }
                    }
                    is CompeteEvent.GetContestCards -> {
                        _state.update { it.copy(
                            contestCards = event.contestCards,
                            filteredContestCards = event.contestCards,
                            isLoading = false
                        ) }
                    }
                    is CompeteEvent.CreatedContestCard -> {
                        _state.update { it.copy(
                            contestCards = it.contestCards + event.contestCardModel,
                            success = "Contest created successfully"
                        ) }
                        filterPracticeCards(state.value.selectedFilter)
                    }
                    CompeteEvent.JoinedContest -> {
                        _state.update { it.copy(success = "Joined contest successfully") }
                        navigationManager.navigateTo(Screen.Waiting.route)
                    }
                    CompeteEvent.GotContestInfoFromCode -> {
                        navigationManager.navigateTo(Screen.Waiting.route)
                    }
                    is CompeteEvent.JoinedContestCard -> {
                        _state.update { it.copy(
                            contestCards = it.contestCards + event.contestCardModel,
                        )}
                        filterPracticeCards(state.value.selectedFilter)
                    }
                }
            }
        }
    }

    private fun updateContestCard(contestCardModel: ContestCardModel) {
        _state.update { it.copy(
            contestCards = it.contestCards.map { card ->
                if (card.id == contestCardModel.id) {
                    contestCardModel
                } else {
                    card
                }
            },
            filteredContestCards = it.filteredContestCards.map { card ->
                if (card.id == contestCardModel.id) {
                    contestCardModel
                } else {
                    card
                }
            }
        ) }
    }

    override fun onCleared() {
        super.onCleared()
        competeRepository.disconnect()
    }
}