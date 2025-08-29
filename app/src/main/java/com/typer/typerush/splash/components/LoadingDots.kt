package com.typer.typerush.splash.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.StartOffset
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.typer.typerush.ui.theme.TypeRushColors

@Composable
fun LoadingDots(
    modifier: Modifier = Modifier,
    dotCount: Int = 5,
    dotSize: Float = 6f,
    primaryColor: Color = TypeRushColors.Primary,
    secondaryColor: Color = TypeRushColors.Secondary
) {
    val infiniteTransition = rememberInfiniteTransition(label = "loadingDots")

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(dotCount) { index ->
            val scale by infiniteTransition.animateFloat(
                initialValue = 0.4f,
                targetValue = 1.2f,
                animationSpec = infiniteRepeatable(
                    animation = tween(800, easing = FastOutSlowInEasing),
                    repeatMode = RepeatMode.Reverse,
                    initialStartOffset = StartOffset(index * 150)
                ),
                label = "dotScale$index"
            )

            val alpha by infiniteTransition.animateFloat(
                initialValue = 0.3f,
                targetValue = 1f,
                animationSpec = infiniteRepeatable(
                    animation = tween(800, easing = FastOutSlowInEasing),
                    repeatMode = RepeatMode.Reverse,
                    initialStartOffset = StartOffset(index * 150)
                ),
                label = "dotAlpha$index"
            )

            val color = if (index % 2 == 0) primaryColor else secondaryColor

            Box(
                modifier = modifier
                    .size((dotSize * scale).dp)
                    .alpha(alpha)
                    .background(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                color,
                                color.copy(alpha = 0.6f)
                            )
                        ),
                        shape = CircleShape
                    )
            )
        }
    }
}