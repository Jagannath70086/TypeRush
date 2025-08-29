package com.typer.typerush.splash.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.typer.typerush.splash.Offset
import com.typer.typerush.ui.theme.TypeRushColors
import kotlin.to

@Composable
fun BackgroundDecorations(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize()) {
        repeat(8) { index ->
            val infiniteTransition = rememberInfiniteTransition(
                label = "backgroundDecoration$index"
            )

            val offsetY by infiniteTransition.animateFloat(
                initialValue = 0f,
                targetValue = 30f,
                animationSpec = infiniteRepeatable(
                    animation = tween(
                        durationMillis = 3000 + (index * 200),
                        easing = FastOutSlowInEasing
                    ),
                    repeatMode = RepeatMode.Reverse
                ),
                label = "floatingY$index"
            )

            val rotation by infiniteTransition.animateFloat(
                initialValue = 0f,
                targetValue = 360f,
                animationSpec = infiniteRepeatable(
                    animation = tween(
                        durationMillis = 10000 + (index * 1000),
                        easing = LinearEasing
                    )
                ),
                label = "rotation$index"
            )

            val positions = listOf(
                Alignment.TopStart to Offset(50.dp, 100.dp),
                Alignment.TopEnd to Offset(-80.dp, 150.dp),
                Alignment.CenterStart to Offset(30.dp, -50.dp),
                Alignment.CenterEnd to Offset(-60.dp, 80.dp),
                Alignment.BottomStart to Offset(100.dp, -120.dp),
                Alignment.BottomEnd to Offset(-40.dp, -80.dp),
                Alignment.Center to Offset(-100.dp, -200.dp),
                Alignment.Center to Offset(120.dp, 150.dp)
            )

            if (index < positions.size) {
                val (alignment, offset) = positions[index]

                Box(
                    modifier = Modifier
                        .align(alignment)
                        .offset(x = offset.x, y = offset.y + offsetY.dp)
                        .size((20 + index * 8).dp)
                        .background(
                            brush = Brush.radialGradient(
                                colors = listOf(
                                    TypeRushColors.Primary.copy(alpha = 0.1f),
                                    Color.Transparent
                                )
                            ),
                            shape = CircleShape
                        )
                )
            }
        }
    }
}