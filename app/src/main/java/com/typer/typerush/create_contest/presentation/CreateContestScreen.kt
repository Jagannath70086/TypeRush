package com.typer.typerush.create_contest.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.TextFields
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.typer.typerush.core.components.SnackBarType
import com.typer.typerush.core.components.TyperSnackBar
import com.typer.typerush.create_contest.presentation.components.CreateContestButton
import com.typer.typerush.create_contest.presentation.components.CreateContestTextField
import com.typer.typerush.create_contest.presentation.components.DifficultyOption
import com.typer.typerush.create_contest.presentation.components.Slider
import com.typer.typerush.ui.theme.TypeRushColors
import com.typer.typerush.ui.theme.TypeRushGradients
import com.typer.typerush.ui.theme.TypeRushTextStyles
import org.koin.compose.koinInject

@Composable
fun CreateContestScreen(
    modifier: Modifier = Modifier,
    viewModel: CreateContestViewModel = koinInject()
) {

    val state by viewModel.state.collectAsState()

    val scrollState = rememberScrollState()

    Box(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(top = 36.dp, start = 20.dp, end = 20.dp),
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Row(
                modifier = modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(
                            Brush.linearGradient(
                                TypeRushGradients.PrimaryGradient.map { it.copy(alpha = 0.1f) }
                            )
                        )
                        .border(
                            1.dp,
                            TypeRushColors.Primary.copy(alpha = 0.3f),
                            RoundedCornerShape(16.dp)
                        )
                        .clickable { viewModel.onIntent(CreateContestIntent.OnBackPressed) },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBackIosNew,
                        contentDescription = "Back",
                        modifier = Modifier.size(22.dp),
                        tint = TypeRushColors.Primary
                    )
                }

                Spacer(modifier = modifier.width(16.dp))

                Text(
                    text = "Create Contest",
                    style = TypeRushTextStyles.gameTitle.copy(
                        fontSize = 28.sp,
                        fontWeight = FontWeight.ExtraBold
                    ),
                    color = TypeRushColors.TextPrimary
                )
            }
            CreateContestTextField(
                value = state.contest.title,
                onValueChange = { viewModel.onIntent(CreateContestIntent.OnTitleChanged(it)) },
                label = "Contest Title",
                placeholder = "Epic Typing Challenge",
                icon = Icons.Default.Event
            )

            CreateContestTextField(
                value = state.contest.textSnippet,
                onValueChange = { viewModel.onIntent(CreateContestIntent.OnTextSnippetChanged(it)) },
                label = "Challenge Text",
                placeholder = "The quick brown fox jumps over the lazy dog...",
                icon = Icons.Default.TextFields,
                minLines = 4,
                maxLines = 6
            )

            CreateContestTextField(
                value = state.contest.tags.joinToString(", "),
                onValueChange = { viewModel.onIntent(CreateContestIntent.OnTagsChanged(it)) },
                label = "Tags",
                placeholder = "Fun, Challenge",
                icon = Icons.Default.Group
            )

            Slider(
                title = "Duration",
                value = state.contest.duration.toFloat() * 60,
                onValueChange = { viewModel.onIntent(CreateContestIntent.OnDurationChanged(it.toInt())) },
                valueRange = 60f..300f,
                steps = 26, // 30, 60, 90, 120, 150, 180, 210, 240, 270, 300
                icon = Icons.Default.Timer,
                unit = "seconds",
                formatValue = { "${it.toInt()}s" }
            )

            Slider(
                title = "Maximum Players",
                value = state.contest.maxPlayers.toFloat(),
                onValueChange = { viewModel.onIntent(CreateContestIntent.OnMaxPlayersChanged(it.toInt())) },
                valueRange = 2f..200f,
                steps = 49, // 2, 4, 6, 8, 10... 100
                icon = Icons.Default.Group,
                unit = "players",
                formatValue = { "${it.toInt()}" }
            )

            Column(
                modifier = modifier,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Speed,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp),
                        tint = TypeRushColors.Primary
                    )
                    Text(
                        text = "Difficulty Level",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 16.sp
                        ),
                        color = TypeRushColors.TextPrimary
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    val difficulties = listOf(
                        "Easy" to "🟢",
                        "Medium" to "🟡",
                        "Hard" to "🔴"
                    )

                    difficulties.forEach { (difficulty, emoji) ->
                        val isSelected = state.contest.difficulty == difficulty.lowercase()
                        DifficultyOption(
                            text = difficulty,
                            emoji = emoji,
                            selected = isSelected,
                            onClick = { viewModel.onIntent(CreateContestIntent.OnDifficultyChanged(difficulty.lowercase())) },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }

            CreateContestButton(
                onClick = { viewModel.onIntent(CreateContestIntent.CreateContest) },
                enabled = state.contest.title.isNotBlank() && state.contest.textSnippet.isNotBlank() && state.contest.tags.isNotEmpty(),
                isLoading = state.isLoading
            )

            Spacer(modifier = modifier.height(32.dp))

        }
        TyperSnackBar(
            message = state.error ?: "",
            type = SnackBarType.ERROR,
            onDismiss = { viewModel.onIntent(CreateContestIntent.DismissError) },
            duration = SnackbarDuration.Indefinite,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
        TyperSnackBar(
            message = state.successMessage ?: "",
            type = SnackBarType.SUCCESS,
            onDismiss = {  },
            duration = SnackbarDuration.Short,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}