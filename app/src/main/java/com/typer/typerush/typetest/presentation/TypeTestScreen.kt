package com.typer.typerush.typetest.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.typer.typerush.core.components.SnackBarType
import com.typer.typerush.core.components.TyperSnackBar
import com.typer.typerush.navigation.NavigationManager
import com.typer.typerush.ui.theme.TypeRushColors
import com.typer.typerush.ui.theme.TypeRushTextStyles
import com.typer.typerush.waiting_page.presentation.WaitingIntent
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TypeTestScreen(
    modifier: Modifier = Modifier,
    type: String = "",
    viewModel: TypeTestViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()

    val focusRequester = remember { FocusRequester() }
    val listState = rememberLazyListState()
    val keyboardController = LocalSoftwareKeyboardController.current

    val totalTime = (state.gameInfo!!.time * 60).toFloat()
    val progress = (state.time.toFloat() / totalTime).coerceIn(0f, 1f)

    val timerColor = when {
        progress > 0.5f -> Color(0xFF4CAF50)
        progress > 0.25f -> Color(0xFFFF9800)
        else -> Color(0xFFF44336)
    }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
        viewModel.onIntent(TypeTestIntent.SetType(type))
    }

    LaunchedEffect(state.currentPosition) {
        val lettersTyped = state.input.length
        if (lettersTyped > 0 && lettersTyped % 16 == 0) {
            listState.animateScrollBy(60f)
        }
    }

    val typedText = buildAnnotatedString {
        state.gameInfo?.text?.forEachIndexed { index, char ->
            when {
                index < state.input.length -> {
                    val userChar = state.input[index]
                    val isCorrect = userChar == char
                    append(
                        AnnotatedString(
                            text = char.toString(),
                            spanStyle = SpanStyle(
                                color = if (isCorrect) {
                                    Color(0xFF4CAF50)
                                } else {
                                    Color(0xFFFF5722)
                                },
                                background = if (index == state.currentPosition - 1) {
                                    Color(0xFF2196F3).copy(alpha = 0.3f)
                                } else Color.Transparent
                            )
                        )
                    )
                }
                index == state.currentPosition -> {
                    append(
                        AnnotatedString(
                            text = char.toString(),
                            spanStyle = SpanStyle(
                                background = Color(0xFF2196F3).copy(alpha = 0.5f),
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        )
                    )
                }
                else -> {
                    append(
                        AnnotatedString(
                            text = char.toString(),
                            spanStyle = SpanStyle(
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
                            )
                        )
                    )
                }
            }
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 36.dp, start = 20.dp, end = 20.dp),
    ) {
        LazyColumn(state = listState) {
            stickyHeader {
                Column(
                    modifier = modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.background)
                        .padding(bottom = 12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(
                        modifier = modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.Bottom,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(
                                text = state.wpm.toString(),
                                style = MaterialTheme.typography.titleLarge,
                                color = MaterialTheme.colorScheme.onBackground,
                                textAlign = TextAlign.Center
                            )
                            Text(
                                text = "WPM",
                                style = MaterialTheme.typography.titleSmall.copy(
                                    fontSize = 10.sp
                                ),
                                color = MaterialTheme.colorScheme.primary,
                                textAlign = TextAlign.Center
                            )
                            Spacer(modifier = modifier.width(8.dp))
                            Text(
                                text = "${state.accuracy}%",
                                style = MaterialTheme.typography.titleLarge,
                                color = MaterialTheme.colorScheme.onBackground,
                                textAlign = TextAlign.Center
                            )
                            Text(
                                text = "Acc",
                                style = MaterialTheme.typography.titleSmall.copy(
                                    fontSize = 10.sp
                                ),
                                color = MaterialTheme.colorScheme.primary,
                                textAlign = TextAlign.Center
                            )
                        }
                        Box{
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(progress)
                                    .background(
                                        brush = Brush.horizontalGradient(
                                            colors = listOf(
                                                timerColor,
                                                timerColor.copy(alpha = 0.1f)
                                            )
                                        ),
                                        shape = MaterialTheme.shapes.small
                                    )
                                    .matchParentSize()
                            )
                            Row(
                                modifier = Modifier
                                    .padding(horizontal = 12.dp, vertical = 8.dp),
                                horizontalArrangement = Arrangement.spacedBy(6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Timer,
                                    contentDescription = "Timer",
                                    modifier = Modifier.size(18.dp),
                                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                                )
                                Text(
                                    text = state.timerValue,
                                    style = MaterialTheme.typography.titleSmall,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                                    fontFamily = FontFamily.Monospace
                                )
                            }
                        }
                    }

                    Row(
                        modifier = modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        val progress = (state.currentPosition.toFloat() / state.gameInfo!!.text.length).coerceIn(0f, 1f)
                        val completedWeight = if (progress > 0f) progress else 0.001f
                        val remainingWeight = if (progress < 1f) 1f - progress else 0.001f

                        Box(
                            modifier = modifier
                                .weight(completedWeight)
                                .height(8.dp)
                                .background(
                                    brush = if (progress > 0f) {
                                        Brush.horizontalGradient(
                                            colors = listOf(
                                                TypeRushColors.Primary,
                                                Color(0xFF00C9FF),
                                                Color(0xFF92FE9D)
                                            )
                                        )
                                    } else {
                                        Brush.horizontalGradient(
                                            colors = listOf(Color.Transparent, Color.Transparent)
                                        )
                                    },
                                    shape = RoundedCornerShape(4.dp)
                                )
                        )

                        Box(
                            modifier = modifier
                                .weight(remainingWeight)
                                .height(8.dp)
                                .background(
                                    color = TypeRushColors.Primary.copy(alpha = 0.1f),
                                    shape = RoundedCornerShape(4.dp)
                                )
                        )
                    }
                }
            }

            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp, horizontal = 6.dp)
                ) {
                    Text(
                        text = typedText,
                        modifier = Modifier.fillMaxWidth(),
                        style = TypeRushTextStyles.typingText.copy(
                            fontSize = 20.sp,
                            lineHeight = 32.sp
                        ),
                        textAlign = TextAlign.Start
                    )

                    BasicTextField(
                        value = state.input,
                        onValueChange = { newInput ->
                            viewModel.onIntent(TypeTestIntent.OnTypedValueChanged(newInput))
                        },
                        modifier = Modifier
                            .fillMaxSize()
                            .focusRequester(focusRequester),
                        textStyle = TypeRushTextStyles.typingText.copy(
                            color = Color.Transparent,
                            fontSize = 20.sp,
                            lineHeight = 32.sp
                        ),
                        cursorBrush = SolidColor(Color.Transparent),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.None
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                keyboardController?.hide()
                            }
                        )
                    )
                }
                Spacer(modifier = modifier.height(290.dp))
            }
        }
        TyperSnackBar(
            message = state.error ?: "",
            type = SnackBarType.ERROR,
            onDismiss = { viewModel.onIntent(TypeTestIntent.DismissError) },
            duration = SnackbarDuration.Indefinite,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
        TyperSnackBar(
            message = state.successMessage ?: "",
            type = SnackBarType.SUCCESS,
            onDismiss = { viewModel.onIntent(TypeTestIntent.DismissSuccess) },
            duration = SnackbarDuration.Short,
            modifier = Modifier.align(Alignment.BottomCenter)
        )

        if (state.isLoading && !state.hasSubmitted) {
            Dialog(
                onDismissRequest = {},
                properties = DialogProperties(
                    dismissOnBackPress = false,
                    dismissOnClickOutside = false,
                    usePlatformDefaultWidth = false
                )
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.7f))
                        .clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                        ) {},
                    contentAlignment = Alignment.Center
                ) {
                    Card(
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
                            ),
                        shape = RoundedCornerShape(24.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surface
                        )
                    ) {
                        Column(
                            modifier = Modifier.padding(32.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Text(
                                text = "Submitting...",
                                style = TypeRushTextStyles.gameTitle.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 22.sp
                                ),
                                color = TypeRushColors.TextPrimary
                            )

                            CircularProgressIndicator()
                        }
                    }
                }
            }
        }
    }
}