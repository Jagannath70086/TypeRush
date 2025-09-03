package com.typer.typerush.waiting_page.presentation

import android.content.ClipData
import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.typer.typerush.ui.theme.TypeRushColors
import com.typer.typerush.ui.theme.TypeRushTextStyles
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.HourglassEmpty
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.TextButton
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.ClipEntry
import androidx.compose.ui.platform.LocalClipboard
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.NativeClipboard
import androidx.compose.ui.window.Dialog
import com.typer.typerush.core.components.SnackBarType
import com.typer.typerush.core.components.TyperSnackBar
import com.typer.typerush.core.session.SessionManager
import com.typer.typerush.landing_home.LandingHomeIntent
import com.typer.typerush.practice.presentation.PracticeIntent
import com.typer.typerush.waiting_page.presentation.components.ParticipantUi
import com.typer.typerush.waiting_page.presentation.components.StartingDialog
import com.typer.typerush.waiting_page.presentation.components.StatusCard
import kotlinx.coroutines.runBlocking
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun WaitingScreen(
    modifier: Modifier = Modifier,
    viewModel: WaitingViewModel = koinViewModel(),
    sessionManager: SessionManager = koinInject()
) {
    val state by viewModel.state.collectAsState()
    val currentUser by sessionManager.currentUser.collectAsState()
    val currentUserIsCreator = state.contestModel?.players?.find { it.isCreator && it.userId == currentUser?.firebaseId } != null

    val clipboardManager = LocalClipboardManager.current
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 36.dp, start = 15.dp, end = 15.dp),
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = modifier.height(2.dp))
            Row(
                modifier = modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBackIosNew,
                    contentDescription = "Back",
                    modifier = Modifier
                        .size(24.dp)
                        .clickable { viewModel.onIntent(WaitingIntent.OnBackPressed) },
                    tint = TypeRushColors.Primary
                )

                Column(
                    modifier = modifier
                        .padding(end = 12.dp)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Join via Code",
                        style = TypeRushTextStyles.buttonText.copy(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.ExtraBold
                        ),
                        color = TypeRushColors.TextPrimary
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = state.contestModel?.contestCode ?: "",
                            style = TypeRushTextStyles.buttonText.copy(
                                fontSize = 18.sp,
                                fontWeight = FontWeight.ExtraBold,
                                letterSpacing = 8.sp,
                                brush = Brush.linearGradient(
                                    colors = listOf(
                                        Color(0xFFFF8008),
                                        Color(0xFFFFC837),
                                        Color(0xFFFF416C)
                                    )
                                )
                            )
                        )

                        Icon(
                            imageVector = Icons.Default.ContentCopy,
                            contentDescription = "Copy",
                            modifier = modifier
                                .size(16.dp)
                                .clickable { viewModel.onIntent(WaitingIntent.OnCodeCopied(clipboardManager)) },
                            tint = Color.Cyan.copy(alpha = 0.8f)
                        )
                    }
                }
            }

            Card(
                modifier = modifier
                    .fillMaxWidth()
                    .animateContentSize(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                border = BorderStroke(
                    width = 1.dp,
                    brush = Brush.linearGradient(
                        colors = listOf(
                            TypeRushColors.Primary.copy(alpha = 0.2f),
                            Color(0xFF00C9FF).copy(alpha = 0.2f),
                            Color(0xFF92FE9D).copy(alpha = 0.2f)
                        )
                    )
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    Text(
                        text = state.contestModel?.title ?: "Test",
                        style = TypeRushTextStyles.gameTitle.copy(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            brush = Brush.linearGradient(
                                colors = listOf(
                                    TypeRushColors.Primary,
                                    Color(0xFF00C9FF),
                                    Color(0xFF92FE9D)
                                )
                            )
                        )
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Timer,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp),
                                tint = TypeRushColors.Primary
                            )
                            Text(
                                text = "${state.contestModel?.duration ?: 1} min",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontWeight = FontWeight.SemiBold
                                ),
                                color = TypeRushColors.TextPrimary
                            )
                        }

                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = when (state.contestModel?.difficulty?.lowercase() ?: "medium") {
                                "easy" -> Color(0xFF66BB6A).copy(alpha = 0.1f)
                                "medium" -> Color(0xFFFFA726).copy(alpha = 0.1f)
                                "hard" -> Color(0xFFEF5350).copy(alpha = 0.1f)
                                else -> TypeRushColors.Primary.copy(alpha = 0.1f)
                            },
                            border = BorderStroke(
                                1.dp,
                                when (state.contestModel?.difficulty?.lowercase() ?: "medium") {
                                    "easy" -> Color(0xFF66BB6A).copy(alpha = 0.4f)
                                    "medium" -> Color(0xFFFFA726).copy(alpha = 0.4f)
                                    "hard" -> Color(0xFFEF5350).copy(alpha = 0.4f)
                                    else -> TypeRushColors.Primary.copy(alpha = 0.4f)
                                }
                            )
                        ) {
                            Text(
                                text = state.contestModel?.difficulty?.replaceFirstChar {
                                    if (it.isLowerCase()) it.titlecase() else it.toString()
                                } ?: "Medium",
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontWeight = FontWeight.SemiBold
                                ),
                                color = when (state.contestModel?.difficulty?.lowercase() ?: "medium") {
                                    "easy" -> Color(0xFF66BB6A)
                                    "medium" -> Color(0xFFFFA726)
                                    "hard" -> Color(0xFFEF5350)
                                    else -> TypeRushColors.Primary
                                }
                            )
                        }
                    }

                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Participants",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontWeight = FontWeight.SemiBold
                                ),
                                color = TypeRushColors.TextPrimary
                            )
                            Text(
                                text = "${state.contestModel?.players?.size ?: 1}/${state.contestModel?.maxPlayers ?: 1}",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontWeight = FontWeight.Bold
                                ),
                                color = TypeRushColors.Primary
                            )
                        }

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(8.dp)
                                .background(
                                    color = TypeRushColors.Primary.copy(alpha = 0.1f),
                                    shape = RoundedCornerShape(4.dp)
                                )
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .fillMaxWidth(
                                        fraction = ((state.contestModel?.players?.size?.toFloat() ?: 1f) / (state.contestModel?.maxPlayers?.toFloat() ?: 1f)).coerceIn(0f, 1f)
                                    )
                                    .background(
                                        brush = Brush.horizontalGradient(
                                            colors = listOf(
                                                TypeRushColors.Primary,
                                                Color(0xFF00C9FF),
                                                Color(0xFF92FE9D)
                                            )
                                        ),
                                        shape = RoundedCornerShape(4.dp)
                                    )
                            )
                        }
                    }

                    if (!state.contestModel?.tags.isNullOrEmpty()) {
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(state.contestModel?.tags!!) { tag ->
                                Surface(
                                    shape = RoundedCornerShape(16.dp),
                                    color = TypeRushColors.Primary.copy(alpha = 0.08f),
                                    border = BorderStroke(
                                        1.dp,
                                        TypeRushColors.Primary.copy(alpha = 0.25f)
                                    )
                                ) {
                                    Text(
                                        text = tag,
                                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                        style = MaterialTheme.typography.bodySmall,
                                        color = TypeRushColors.Primary
                                    )
                                }
                            }
                        }
                    }
                }
            }

            Card(
                modifier = modifier
                    .fillMaxWidth()
                    .animateContentSize(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                border = BorderStroke(
                    width = 1.dp,
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color(0xFFFFC107).copy(alpha = 0.3f),
                            Color(0xFFFFD54F).copy(alpha = 0.3f),
                            Color(0xFFFFA726).copy(alpha = 0.3f)
                        )
                    )
                )
            ) {
                Row(
                    modifier = modifier
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = "Leaderboard",
                        modifier = Modifier.size(24.dp),
                        tint = Color(0xFFFFC107)
                    )
                    Text(
                        text = "You need to be on this screen to participate",
                        style = TypeRushTextStyles.buttonText.copy(
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        ),
                        color = TypeRushColors.TextPrimary
                    )
                }
            }

            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.EmojiEvents,
                    contentDescription = "Leaderboard",
                    modifier = Modifier.size(24.dp),
                    tint = TypeRushColors.Primary
                )
                Text(
                    text = "Leaderboard",
                    style = TypeRushTextStyles.buttonText.copy(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.ExtraBold
                    ),
                    color = TypeRushColors.TextPrimary
                )
            }

            LazyColumn {
                itemsIndexed(state.contestModel?.players!!.sortedBy { it.wpm }) { index, player ->
                    ParticipantUi(
                        number = index,
                        name = player.userName,
                        isCreator = player.isCreator,
                        wpm = player.wpm,
                        accuracy = player.accuracy,
                        hasFinished = player.hasFinished
                    )
                    Spacer(modifier = modifier.height(12.dp))
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }

        if (currentUserIsCreator && state.contestModel?.status!!.uppercase() == "WAITING") {
            ElevatedButton(
                onClick = {
                    Log.i("WaitingScreen", "WaitingScreen: Starting room")
                    viewModel.onIntent(WaitingIntent.OnStartPressed) },
                modifier = modifier
                    .fillMaxWidth()
                    .padding(bottom = 4.dp)
                    .align(Alignment.BottomCenter)
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
                enabled = state.isConnected && !state.isLoading && !state.hasStarted
            ) {
                Text("Start Room",
                    style = TypeRushTextStyles.buttonText,
                    color = TypeRushColors.TextPrimary
                )
            }
        }

        else {
            state.contestModel?.status?.let { status ->
                StatusCard(status = status, modifier = modifier.align(Alignment.BottomCenter))
            }
        }

        TyperSnackBar(
            message = state.error ?: "",
            type = SnackBarType.ERROR,
            onDismiss = { viewModel.onIntent(WaitingIntent.DismissError) },
            duration = SnackbarDuration.Indefinite,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
        TyperSnackBar(
            message = state.successMessage ?: "",
            type = SnackBarType.SUCCESS,
            onDismiss = { viewModel.onIntent(WaitingIntent.DismissSuccess) },
            duration = SnackbarDuration.Short,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
    if (state.showStartDialog) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            Color.Black.copy(alpha = 0.6f),
                            Color.Black.copy(alpha = 0.8f)
                        ),
                        radius = 1000f
                    )
                )
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() },
                    enabled = !state.isLoading
                ) {
                    if (!state.isLoading) {
                        viewModel.onIntent(WaitingIntent.DismissStartDialog)
                    }
                }
        )
        AlertDialog(
            onDismissRequest = {
                if (!state.isLoading) {
                    viewModel.onIntent(WaitingIntent.DismissStartDialog)
                }
            },
            title = {
                Text(
                    text = "Starting Room",
                    style = TypeRushTextStyles.gameTitle.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp
                    ),
                    color = TypeRushColors.TextPrimary
                )
            },
            text = {
                Text(
                    text = "Are you sure you want to start the room? This will start the contest for everyone joined.",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        lineHeight = 20.sp
                    ),
                    color = TypeRushColors.TextSecondary
                )
            },
            confirmButton = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    TextButton(
                        onClick = {
                            if (!state.isLoading) {
                                viewModel.onIntent(WaitingIntent.DismissStartDialog)
                            }
                        },
                        colors = ButtonDefaults.textButtonColors(
                            contentColor = if (state.isLoading)
                                TypeRushColors.TextSecondary.copy(alpha = 0.4f)
                            else
                                TypeRushColors.TextSecondary
                        ),
                        modifier = Modifier
                            .height(36.dp)
                            .border(
                                width = 1.dp,
                                color = if (state.isLoading)
                                    Color.White.copy(alpha = 0.3f)
                                else
                                    Color.White.copy(alpha = 0.8f),
                                shape = RoundedCornerShape(16.dp)
                            )
                            .height(24.dp),
                        enabled = !state.isLoading
                    ) {
                        Text(
                            text = "Cancel",
                            style = MaterialTheme.typography.labelLarge.copy(
                                fontWeight = FontWeight.Medium
                            ),
                            color = if (state.isLoading)
                                TypeRushColors.TextSecondary.copy(alpha = 0.4f)
                            else
                                TypeRushColors.TextSecondary
                        )
                    }
                    ElevatedButton(
                        onClick = {
                            viewModel.onIntent(WaitingIntent.OnActuallyStarted)
                        },
                        colors = ButtonDefaults.elevatedButtonColors(
                            containerColor = Color(0xFFE57373),
                            contentColor = Color.White,
                            disabledContainerColor = Color(0xFFE57373).copy(alpha = 0.7f),
                        ),
                        elevation = ButtonDefaults.elevatedButtonElevation(
                            defaultElevation = 4.dp,
                            pressedElevation = 8.dp
                        ),
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier.padding(start = 8.dp),
                        enabled = !state.isLoading
                    ) {
                        if (state.isLoading) {
                            CircularProgressIndicator(
                                color = Color.White,
                                strokeWidth = 2.dp,
                                modifier = Modifier.size(16.dp)
                            )
                        } else {
                            Text(
                                text = "Start",
                                style = MaterialTheme.typography.labelLarge.copy(
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }
                    }
                }
            },
            containerColor = MaterialTheme.colorScheme.surface,
            titleContentColor = TypeRushColors.TextPrimary,
            textContentColor = TypeRushColors.TextSecondary,
            shape = RoundedCornerShape(24.dp),
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .shadow(
                    elevation = 12.dp,
                    shape = RoundedCornerShape(24.dp),
                    ambientColor = Color.Black.copy(alpha = 0.1f),
                    spotColor = Color.Black.copy(alpha = 0.15f)
                )
                .border(
                    width = 1.dp,
                    color = Color.White.copy(alpha = 0.8f),
                    shape = RoundedCornerShape(24.dp)
                )
        )
    }

    StartingDialog(state.hasStarted)
}