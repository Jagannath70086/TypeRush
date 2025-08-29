package com.typer.typerush.core.components

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

enum class SnackBarType {
    SUCCESS,
    ERROR,
    WARNING,
    INFO
}

@Composable
fun TyperSnackBar(
    message: String,
    type: SnackBarType,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    duration: SnackbarDuration = SnackbarDuration.Long,
    showIcon: Boolean = true
) {
    val snackBarHostState = remember { SnackbarHostState() }

    val (containerColor, contentColor, icon) = when (type) {
        SnackBarType.SUCCESS -> Triple(
            Color(0xFF4CAF50), // Green
            Color.White,
            Icons.Default.CheckCircle
        )
        SnackBarType.ERROR -> Triple(
            MaterialTheme.colorScheme.errorContainer,
            MaterialTheme.colorScheme.onErrorContainer,
            Icons.Default.Error
        )
        SnackBarType.WARNING -> Triple(
            Color(0xFFFF9800), // Orange
            Color.White,
            Icons.Default.Warning
        )
        SnackBarType.INFO -> Triple(
            MaterialTheme.colorScheme.primaryContainer,
            MaterialTheme.colorScheme.onPrimaryContainer,
            Icons.Default.Info
        )
    }

    LaunchedEffect(message) {
        if (message.isNotEmpty()) {
            snackBarHostState.showSnackbar(
                message = message,
                duration = duration
            )
        }
    }

    LaunchedEffect(message, duration) {
        if (message.isNotEmpty() && duration != SnackbarDuration.Indefinite) {
            val delayTime = when (duration) {
                SnackbarDuration.Short -> 4000L
                SnackbarDuration.Long -> 6000L
                else -> 0L
            }
            if (delayTime > 0) {
                delay(delayTime)
                onDismiss()
            }
        }
    }

    SnackbarHost(
        hostState = snackBarHostState,
        modifier = modifier,
        snackbar = { snackbarData ->
            CustomSnackbar(
                message = snackbarData.visuals.message,
                containerColor = containerColor,
                contentColor = contentColor,
                icon = if (showIcon) icon else null,
                onDismiss = onDismiss
            )
        }
    )
}

@Composable
private fun CustomSnackbar(
    message: String,
    containerColor: Color,
    contentColor: Color,
    icon: ImageVector?,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        visible = true
    }

    AnimatedVisibility(
        visible = visible,
        enter = slideInVertically(
            initialOffsetY = { it },
            animationSpec = tween(300, easing = EaseOutCubic)
        ) + fadeIn(animationSpec = tween(300)),
        exit = slideOutVertically(
            targetOffsetY = { it },
            animationSpec = tween(250, easing = EaseInCubic)
        ) + fadeOut(animationSpec = tween(250))
    ) {
        Card(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .shadow(
                    elevation = 8.dp,
                    shape = RoundedCornerShape(12.dp)
                ),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(
                containerColor = containerColor
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (icon != null) {
                    var iconVisible by remember { mutableStateOf(false) }

                    LaunchedEffect(Unit) {
                        delay(150)
                        iconVisible = true
                    }

                    AnimatedVisibility(
                        visible = iconVisible,
                        enter = scaleIn(
                            animationSpec = spring(
                                dampingRatio = Spring.DampingRatioMediumBouncy,
                                stiffness = Spring.StiffnessLow
                            )
                        )
                    ) {
                        Icon(
                            imageVector = icon,
                            contentDescription = null,
                            tint = contentColor,
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))
                }

                Text(
                    text = message,
                    color = contentColor,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Medium
                    ),
                    modifier = Modifier.weight(1f)
                )

                Spacer(modifier = Modifier.width(8.dp))

                IconButton(
                    onClick = {
                        visible = false
                        onDismiss()
                    },
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Dismiss",
                        tint = contentColor.copy(alpha = 0.8f),
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    }
}