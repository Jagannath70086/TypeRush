package com.typer.typerush.compete.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowCircleRight
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.typer.typerush.compete.presentation.components.ContentSeparator
import com.typer.typerush.compete.presentation.components.ContestCard
import com.typer.typerush.compete.presentation.components.ContestCardSkeleton
import com.typer.typerush.compete.presentation.components.StatusChip
import com.typer.typerush.core.components.CustomTextField
import com.typer.typerush.core.components.FilterChip
import com.typer.typerush.core.components.SnackBarType
import com.typer.typerush.core.components.TyperSnackBar
import com.typer.typerush.ui.theme.TypeRushColors
import com.typer.typerush.ui.theme.TypeRushTextStyles
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun CompeteScreen(
    modifier: Modifier = Modifier,
    viewModel: CompeteViewModel = koinViewModel()
) {
    val tags = remember { listOf("All", "My Contests", "Waiting", "Completed") }
    val state by viewModel.state.collectAsState()

    Box {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                StatusChip(
                    connected = state.isConnected,
                    modifier = modifier
                        .clickable(
                            enabled = !state.isConnected,
                            onClick = { viewModel.onIntent(CompeteIntent.AttemptReconnection) }
                        )
                )
            }

            stickyHeader {
                Column(
                    modifier = modifier
                        .background(MaterialTheme.colorScheme.background),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Join Contest via Code",
                        style = TypeRushTextStyles.gameTitle.copy(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        ),
                        color = TypeRushColors.TextPrimary
                    )
                    Spacer(modifier = modifier.height(6.dp))
                    CustomTextField(
                        value = state.contestCodeWritingText,
                        onValueChange = { viewModel.onIntent(CompeteIntent.EnterContestCode(it)) },
                        label = "Enter Contest Code",
                        trailingIcon = Icons.Outlined.ArrowCircleRight,
                        onClick = { viewModel.onIntent(CompeteIntent.JoinByContestCode) }
                    )
                    Text("or",
                        style = TypeRushTextStyles.gameTitle.copy(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        ),
                        color = TypeRushColors.TextPrimary,
                        modifier = modifier.padding(12.dp)
                    )
                    ElevatedButton(
                        onClick = { viewModel.onIntent(CompeteIntent.CreateNewContest) },
                        modifier = modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        shape = RoundedCornerShape(32.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = TypeRushColors.Primary,
                            disabledContainerColor = TypeRushColors.Primary.copy(alpha = 0.7f)
                        ),
                        elevation = ButtonDefaults.elevatedButtonElevation(
                            defaultElevation = 8.dp,
                            pressedElevation = 12.dp,
                            disabledElevation = 4.dp
                        ),
                    ) {
                        Text("Create New Contest",
                            style = TypeRushTextStyles.buttonText,
                            color = TypeRushColors.TextPrimary
                        )
                    }

                    ContentSeparator()
                }
            }

            item {
                LazyRow(
                    contentPadding = PaddingValues(bottom = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    items(tags) { tag ->
                        FilterChip(
                            text = tag,
                            isSelected = tag == state.selectedFilter,
                            onSelected = { viewModel.onIntent(CompeteIntent.FilterPracticeCards(tag)) }
                        )
                    }
                }
            }
            if (state.isLoading) {
                items(2) {
                    ContestCardSkeleton()
                }
            } else {
                items(state.filteredContestCards) { contestCard ->
                    ContestCard(
                        title = contestCard.title,
                        authorName = contestCard.authorName,
                        contestCode = contestCard.contestCode,
                        status = contestCard.status,
                        tags = contestCard.tags,
                        time = "${contestCard.time} min",
                        currentPlayers = contestCard.currentPlayers,
                        totalPlayers = contestCard.totalPlayers,
                        onClick = { viewModel.onIntent(CompeteIntent.EnterWaitingPage(contestCard.contestCode)) }
                    )
                }
            }
        }
        TyperSnackBar(
            message = state.error ?: "",
            type = SnackBarType.ERROR,
            onDismiss = { viewModel.onIntent(CompeteIntent.DismissError) },
            duration = SnackbarDuration.Indefinite,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
        TyperSnackBar(
            message = state.success ?: "",
            type = SnackBarType.SUCCESS,
            onDismiss = { viewModel.onIntent(CompeteIntent.DismissSuccess) },
            duration = SnackbarDuration.Short,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}