package com.typer.typerush.practice.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Replay
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.typer.typerush.core.components.SnackBarType
import com.typer.typerush.core.components.TyperSnackBar
import com.typer.typerush.core.session.SessionManager
import com.typer.typerush.practice.presentation.components.FilterChip
import com.typer.typerush.practice.presentation.components.PracticeCard
import com.typer.typerush.practice.presentation.components.PracticeCardSkeleton
import com.typer.typerush.practice.presentation.components.RetryButton
import com.typer.typerush.ui.theme.TypeRushColors
import com.typer.typerush.ui.theme.TypeRushTextStyles
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel
import java.util.Locale

@Composable
fun PracticeScreen(
    modifier: Modifier = Modifier,
    practiceViewModel: PracticeViewModel = koinViewModel(),
    sessionManager: SessionManager = koinInject()
) {
    val user by sessionManager.currentUser.collectAsState()
    val state by practiceViewModel.state.collectAsState()

    val tags = remember { listOf("All", "Beginner", "Intermediate", "Advanced", "Pro") }

    Box {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .height(50.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                AsyncImage(
                    model = user?.photoUrl,
                    contentDescription = "User Profile Picture",
                    modifier = modifier
                        .size(40.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = modifier.width(4.dp))
                Text(
                    text = user?.name?.replaceFirstChar {
                        if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
                    } ?: "User",
                    style = TypeRushTextStyles.gameTitle.copy(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    color = TypeRushColors.TextPrimary
                )
                Spacer(modifier = modifier.weight(1f))
                RetryButton(
                    onClick = { practiceViewModel.onIntent(PracticeIntent.Retry) }
                )
            }

            LazyColumn(
                modifier = modifier.fillMaxSize(),
                contentPadding = PaddingValues(vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    LazyRow(
                        contentPadding = PaddingValues(vertical = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        items(tags) { tag ->
                            FilterChip(
                                text = tag,
                                isSelected = tag == state.selectedFilter,
                                onSelected = {
                                    practiceViewModel.onIntent(
                                        PracticeIntent.FilterPracticeCards(
                                            tag
                                        )
                                    )
                                }
                            )
                        }
                        item {
                            Text(
                                text = "Clear",
                                modifier = modifier
                                    .clip(RoundedCornerShape(50))
                                    .clickable(
                                        onClick = { practiceViewModel.onIntent(PracticeIntent.ClearFilter) }
                                    )
                                    .padding(vertical = 6.dp, horizontal = 8.dp),
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = MaterialTheme.colorScheme.primary
                                )
                            )
                        }
                    }
                }

                if (state.isLoading) {
                    items(3) {
                        PracticeCardSkeleton()
                    }
                } else {
                    items(state.filteredPracticeCards) { (id, title, tags, time) ->
                        PracticeCard(
                            title = title,
                            tags = tags,
                            time = "$time min",
                            onClick = { practiceViewModel.onIntent(PracticeIntent.StartTypeTest(id)) }
                        )
                    }
                }
            }
        }
        TyperSnackBar(
            message = state.error ?: "",
            type = SnackBarType.ERROR,
            onDismiss = { practiceViewModel.onIntent(PracticeIntent.DismissError) },
            duration = SnackbarDuration.Indefinite,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}