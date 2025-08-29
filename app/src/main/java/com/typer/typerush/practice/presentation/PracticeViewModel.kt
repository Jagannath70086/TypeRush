package com.typer.typerush.practice.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.typer.typerush.core.either.Either
import com.typer.typerush.navigation.NavigationManager
import com.typer.typerush.navigation.Screen
import com.typer.typerush.practice.domain.repository.PracticeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PracticeViewModel(
    private val practiceRepository: PracticeRepository,
    private val navigationManager: NavigationManager
): ViewModel() {

    private val _state = MutableStateFlow(PracticeState())
    val state = _state
        .onStart { onIntent(PracticeIntent.GetPracticeCards) }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            _state.value
        )

    fun onIntent(intent: PracticeIntent) {
        when (intent) {
            is PracticeIntent.GetPracticeCards -> getPracticeCards()
            is PracticeIntent.Retry -> refetchPracticeCards()
            is PracticeIntent.FilterPracticeCards -> filterPracticeCards(intent.query)
            is PracticeIntent.ClearFilter -> clearFilter()
            is PracticeIntent.DismissError -> dismissError()
            is PracticeIntent.StartTypeTest -> startTypeTest(intent.id)
        }
    }

    private fun getPracticeCards() {
        _state.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            when (val result = practiceRepository.getPracticeCards()) {
                is Either.Right -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = null,
                            practiceCards = result.value,
                            filteredPracticeCards = result.value
                        )
                    }
                }
                is Either.Left -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = result.error.message
                        )
                    }
                }
            }
        }
    }

    private fun refetchPracticeCards() {
        if (_state.value.practiceCards.isEmpty()) {
            onIntent(PracticeIntent.GetPracticeCards)
        } else {
            _state.update { it.copy(error = "Practice Cards already loaded") }
        }
    }

    private fun filterPracticeCards(query: String) {
        val filteredCards = if (query.equals("All", ignoreCase = true)) {
            _state.value.practiceCards
        } else {
            _state.value.practiceCards.filter { card ->
                card.tags.any { it.equals(query, ignoreCase = true) }
            }
        }
        _state.update { it.copy(filteredPracticeCards = filteredCards, selectedFilter = query) }
    }

    private fun clearFilter() {
        _state.update {
            it.copy(
                filteredPracticeCards = it.practiceCards,
                selectedFilter = "All"
            )
        }
    }

    private fun dismissError() {
        _state.update {
            it.copy(error = null)
        }
    }

    private fun startTypeTest(id: String) {
        viewModelScope.launch {
            navigationManager.navigateTo("${Screen.TypeTest.route}/$id")
        }
    }
}