package com.typer.typerush.practice.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.unit.dp
import com.typer.typerush.core.components.SnackBarType
import com.typer.typerush.core.components.TyperSnackBar
import com.typer.typerush.core.components.FilterChip
import com.typer.typerush.practice.presentation.components.PracticeCard
import com.typer.typerush.practice.presentation.components.PracticeCardSkeleton
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun PracticeScreen(
    modifier: Modifier = Modifier,
    practiceViewModel: PracticeViewModel = koinViewModel()
) {
    val state by practiceViewModel.state.collectAsState()

    val tags = remember { listOf("All", "Beginner", "Intermediate", "Advanced", "Pro") }

    Box {
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            contentPadding = PaddingValues(vertical = 8.dp, horizontal = 12.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
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
        TyperSnackBar(
            message = state.error ?: "",
            type = SnackBarType.ERROR,
            onDismiss = { practiceViewModel.onIntent(PracticeIntent.DismissError) },
            duration = SnackbarDuration.Indefinite,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}