package com.typer.typerush.core.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Replay
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp

@Composable
fun RetryButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var rotation by remember { mutableFloatStateOf(25f) }
    val animatedRotation by animateFloatAsState(
        targetValue = rotation,
        animationSpec = tween(durationMillis = 350, easing = LinearEasing),
        label = "rotationAnim"
    )

    IconButton(
        onClick = {
            rotation += 360f
            onClick()
        },
        modifier = modifier
    ) {
        Icon(
            imageVector = Icons.Default.Replay,
            contentDescription = "Retry",
            tint = Color(0xFF4A90E2),
            modifier = Modifier
                .size(28.dp)
                .graphicsLayer {
                    scaleX = -1f
                    rotationZ = animatedRotation
                }
        )
    }
}
