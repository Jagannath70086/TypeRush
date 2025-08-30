package com.typer.typerush.compete.presentation

import androidx.compose.foundation.background
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
import androidx.compose.material.icons.outlined.ArrowCircleRight
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.typer.typerush.compete.presentation.components.ContentSeparator
import com.typer.typerush.compete.presentation.components.ContestCard
import com.typer.typerush.compete.presentation.components.ContestCardSkeleton
import com.typer.typerush.compete.presentation.components.StatusChip
import com.typer.typerush.core.components.CustomTextField
import com.typer.typerush.core.components.FilterChip
import com.typer.typerush.core.components.SnackBarType
import com.typer.typerush.core.components.TyperSnackBar
import com.typer.typerush.core.session.SessionManager
import com.typer.typerush.core.components.RetryButton
import com.typer.typerush.ui.theme.TypeRushColors
import com.typer.typerush.ui.theme.TypeRushTextStyles
import org.koin.compose.koinInject
import java.util.Locale

@Composable
fun CompeteScreen(
    modifier: Modifier = Modifier
) {
    var text by remember { mutableStateOf("") }

    val tags = remember { listOf("All", "My Contests", "Waiting", "Completed") }

    Box {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                StatusChip(connected = true)
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
                        value = text,
                        onValueChange = { text = it },
                        label = "Enter Contest Code",
                        trailingIcon = Icons.Outlined.ArrowCircleRight,
                        onClick = { /*waitingContestViewModel.joinContestByCode(contestCode = text)*/ }
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
                        onClick = { /*competeViewModel.createNewContest()*/ },
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
                            isSelected = tag == "All",
                            onSelected = {
//                                    practiceViewModel.onIntent(
//                                        PracticeIntent.FilterPracticeCards(
//                                            tag
//                                        )
//                                    )
                            }
                        )
                    }
                }
            }
            if (false) {
                items(2) {
                    ContestCardSkeleton()
                }
            } else {
                items(10) {
                    ContestCard(
                        title = "Enigma",
                        authorName = "Jagannath Sadangi",
                        contestCode = "HAVA5M",
                        status = "waiting",
                        tags = listOf("Beginner", "Natural", "Words", "Easy"),
                        time = "2 min",
                        currentPlayers = 2,
                        totalPlayers = 10,
                        onClick = { /*practiceViewModel.onIntent(PracticeIntent.StartTypeTest(id))*/ }
                    )
                }
            }
        }
        TyperSnackBar(
            message = /*state.error ?:*/ "",
            type = SnackBarType.ERROR,
            onDismiss = {  },
            duration = SnackbarDuration.Indefinite,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}