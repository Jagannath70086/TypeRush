package com.typer.typerush.create_contest.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.typer.typerush.core.either.Either
import com.typer.typerush.core.websocket.WebSocketEvent
import com.typer.typerush.create_contest.domain.models.ContestModel
import com.typer.typerush.create_contest.domain.repository.CreateContestRepository
import com.typer.typerush.navigation.NavigationManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CreateContestViewModel(
    private val navigationManager: NavigationManager,
    private val createContestRepository: CreateContestRepository
): ViewModel() {

    private val _state = MutableStateFlow(CreateContestState(contest = ContestModel()))
    val state = _state.asStateFlow()

    init {
        observeEvents()
    }

    fun onIntent(intent: CreateContestIntent) {
        when(intent) {
            is CreateContestIntent.OnTitleChanged -> handleTitleChange(intent.title)
            is CreateContestIntent.OnTextSnippetChanged -> handleTextChange(intent.textSnippet)
            is CreateContestIntent.OnTagsChanged -> handleTagsChange(intent.tags)
            is CreateContestIntent.OnDurationChanged -> handleDurationChange(intent.duration)
            is CreateContestIntent.OnMaxPlayersChanged -> handleMaxPlayersChange(intent.maxPlayers)
            is CreateContestIntent.OnDifficultyChanged -> handleDifficultyChange(intent.difficulty)
            is CreateContestIntent.CreateContest -> onCreateContest()
            is CreateContestIntent.DismissError -> _state.value = _state.value.copy(error = null)
            is CreateContestIntent.OnBackPressed -> onBackPressed()
        }
    }

    private fun observeEvents() {
        viewModelScope.launch {
            createContestRepository.getSocketEvents().collect { event ->
                when (event) {
                    is WebSocketEvent.Error -> {
                        _state.update { it.copy(error = event.message) }
                    }
                    WebSocketEvent.Connected -> {
                        _state.update { it.copy(isConnected = true) }
                    }
                    WebSocketEvent.Disconnected -> {
                        _state.update { it.copy(isConnected = false) }
                    }
                }
            }
        }

        viewModelScope.launch {
            createContestRepository.getCreateContestEvents().collect { event ->
                when (event) {
                    is CreateContestEvent.CreatedContest -> contestCreated(event.contestModel)
                    is CreateContestEvent.Error -> {
                        _state.update { it.copy(error = event.message) }
                    }
                }
            }
        }
    }

    private fun handleTitleChange(title: String) {
        val validationResult = validateTitle(title)

        _state.value = _state.value.copy(
            contest = _state.value.contest.copy(title = title),
            error = validationResult.errorMessage
        )
    }

    private fun handleTextChange(text: String) {
        val validationResult = validateText(text)

        _state.value = _state.value.copy(
            contest = _state.value.contest.copy(textSnippet = text),
            error = validationResult.errorMessage
        )
    }

    private fun handleTagsChange(tags: String) {
        _state.value = _state.value.copy(contest = state.value.contest.copy(tags = tags.split(",").map { it.trim() }))
    }


    private fun handleDurationChange(duration: Int) {
        _state.value = _state.value.copy(contest = state.value.contest.copy(duration = duration/60))
    }

    private fun handleMaxPlayersChange(maxPlayers: Int) {
        _state.value = _state.value.copy(contest = state.value.contest.copy(maxPlayers = maxPlayers))
    }

    private fun handleDifficultyChange(difficulty: String) {
        _state.value = _state.value.copy(contest = state.value.contest.copy(difficulty = difficulty))
    }

    private fun isContestValid(): Boolean {
        val titleValid = validateTitle(_state.value.contest.title).isValid
        val textValid = validateText(_state.value.contest.textSnippet).isValid
        val tagsValid = validateTags(_state.value.contest.tags.joinToString(", ")).isValid

        return titleValid && textValid && tagsValid
    }
    private fun onCreateContest() {
        if (!isContestValid()) {
            _state.value = _state.value.copy(error = "Please fill in all the required fields")
            return
        }
        _state.value = _state.value.copy(isLoading = true)
        viewModelScope.launch {
            val createdContest = createContestRepository.createContest(_state.value.contest)
            if (createdContest is Either.Left) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = createdContest.error.message
                )
            }
        }
    }

    private fun contestCreated(contestModel: ContestModel) {
        _state.value = _state.value.copy(
            isLoading = false,
            contest = contestModel,
            successMessage = "Contest created successfully"
        )
        viewModelScope.launch {
            navigationManager.navigateBack()
        }
    }
    private fun onBackPressed() {
        viewModelScope.launch {
            navigationManager.navigateBack()
        }
    }

    data class ValidationResult(
        val isValid: Boolean,
        val errorMessage: String? = null
    )

    private fun validateTitle(title: String): ValidationResult {
        return when {
            title.isBlank() -> ValidationResult(false, "Title cannot be empty")
            title.length < 3 -> ValidationResult(false, "Title must be at least 3 characters long")
            title.length > 50 -> ValidationResult(false, "Title must be less than 50 characters")
            title.any { it.isDigit() } -> ValidationResult(false, "Title cannot contain numbers")
            title.any { !it.isLetter() && !it.isWhitespace() && it != '\'' && it != '-' } ->
                ValidationResult(false, "Title can only contain letters, spaces, hyphens, and apostrophes")
            title.trim() != title -> ValidationResult(false, "Title cannot start or end with spaces")
            title.contains(Regex("\\s{2,}")) -> ValidationResult(false, "Title cannot contain multiple consecutive spaces")
            else -> ValidationResult(true)
        }
    }

    private fun validateText(text: String): ValidationResult {
        return when {
            text.isBlank() -> ValidationResult(false, "Text snippet cannot be empty")
            text.length < 10 -> ValidationResult(false, "Text snippet must be at least 10 characters long")
            text.length > 1000 -> ValidationResult(false, "Text snippet must be less than 1000 characters")
            text.trim() != text -> ValidationResult(false, "Text snippet cannot start or end with spaces")
            else -> ValidationResult(true)
        }
    }

    private fun validateTags(tags: String): ValidationResult {
        if (tags.isBlank()) {
            return ValidationResult(false, "At least one tag is required")
        }

        val tagsList = tags.split(",").map { it.trim() }.filter { it.isNotEmpty() }

        return when {
            tagsList.isEmpty() -> ValidationResult(false, "At least one tag is required")
            tagsList.size > 5 -> ValidationResult(false, "Maximum 5 tags allowed")
            tagsList.any { it.length < 2 } -> ValidationResult(false, "Each tag must be at least 2 characters long")
            tagsList.any { it.length > 20 } -> ValidationResult(false, "Each tag must be less than 20 characters")
            tagsList.any { tag -> tag.any { !it.isLetter() && !it.isWhitespace() } } ->
                ValidationResult(false, "Tags can only contain letters and spaces")
            tagsList.any { it.any { char -> char.isDigit() } } ->
                ValidationResult(false, "Tags cannot contain numbers")
            tagsList.size != tagsList.distinct().size ->
                ValidationResult(false, "Duplicate tags are not allowed")
            else -> ValidationResult(true)
        }
    }
}