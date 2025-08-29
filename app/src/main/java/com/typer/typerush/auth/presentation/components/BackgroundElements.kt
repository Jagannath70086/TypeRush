package com.typer.typerush.auth.presentation.components

import androidx.compose.animation.core.FastOutSlowInEasing
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.typer.typerush.ui.theme.TypeRushColors
import kotlin.to

@Composable
fun BackgroundElements() {
    Box(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .size(300.dp)
                .offset(x = (-100).dp, y = 100.dp)
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            TypeRushColors.AnimatedPurple,
                            Color.Transparent
                        )
                    ),
                    CircleShape
                )
                .blur(50.dp)
        )

        Box(
            modifier = Modifier
                .size(200.dp)
                .align(Alignment.TopEnd)
                .offset(x = 50.dp, y = (-50).dp)
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            TypeRushColors.AnimatedCoral,
                            Color.Transparent
                        )
                    ),
                    CircleShape
                )
                .blur(40.dp)
        )

        Box(
            modifier = Modifier
                .size(150.dp)
                .align(Alignment.BottomStart)
                .offset(x = (-30).dp, y = 30.dp)
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            TypeRushColors.AnimatedBlue,
                            Color.Transparent
                        )
                    ),
                    CircleShape
                )
                .blur(35.dp)
        )
    }

    Box(modifier = Modifier.fillMaxSize()) {
        val letters = listOf(
            "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M",
            "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"
        )

        repeat(18) { index ->
            val infiniteTransition = rememberInfiniteTransition(
                label = "letter$index"
            )

            val offsetY by infiniteTransition.animateFloat(
                initialValue = -20f,
                targetValue = 40f,
                animationSpec = infiniteRepeatable(
                    animation = tween(
                        durationMillis = 3000 + (index * 400),
                        easing = FastOutSlowInEasing
                    ),
                    repeatMode = RepeatMode.Reverse
                ),
                label = "letterY$index"
            )

            val rotation by infiniteTransition.animateFloat(
                initialValue = -15f,
                targetValue = 15f,
                animationSpec = infiniteRepeatable(
                    animation = tween(
                        durationMillis = 2500 + (index * 300),
                        easing = FastOutSlowInEasing
                    ),
                    repeatMode = RepeatMode.Reverse
                ),
                label = "letterRotation$index"
            )

            val alpha by infiniteTransition.animateFloat(
                initialValue = 0.08f,
                targetValue = 0.25f,
                animationSpec = infiniteRepeatable(
                    animation = tween(
                        durationMillis = 2000 + (index * 250),
                        easing = FastOutSlowInEasing
                    ),
                    repeatMode = RepeatMode.Reverse
                ),
                label = "letterAlpha$index"
            )

            val scale by infiniteTransition.animateFloat(
                initialValue = 0.8f,
                targetValue = 1.2f,
                animationSpec = infiniteRepeatable(
                    animation = tween(
                        durationMillis = 4000 + (index * 200),
                        easing = FastOutSlowInEasing
                    ),
                    repeatMode = RepeatMode.Reverse
                ),
                label = "letterScale$index"
            )

            val positions = listOf(
                Alignment.TopStart to Pair(60.dp, 100.dp),
                Alignment.TopEnd to Pair((-80).dp, 140.dp),
                Alignment.CenterStart to Pair(40.dp, (-60).dp),
                Alignment.CenterEnd to Pair((-70).dp, 80.dp),
                Alignment.BottomStart to Pair(120.dp, (-140).dp),
                Alignment.BottomEnd to Pair((-50).dp, (-80).dp),
                Alignment.TopCenter to Pair((-90).dp, 220.dp),
                Alignment.BottomCenter to Pair(90.dp, (-180).dp),
                Alignment.Center to Pair((-120).dp, (-200).dp),
                Alignment.Center to Pair(140.dp, 160.dp),
                Alignment.TopStart to Pair(180.dp, 320.dp),
                Alignment.BottomEnd to Pair((-120).dp, (-220).dp),
                Alignment.TopEnd to Pair((-150).dp, 50.dp),
                Alignment.BottomStart to Pair(30.dp, (-250).dp),
                Alignment.CenterStart to Pair((-30).dp, 200.dp),
                Alignment.CenterEnd to Pair(200.dp, (-100).dp),
                Alignment.TopCenter to Pair(50.dp, 180.dp),
                Alignment.BottomCenter to Pair((-80).dp, (-120).dp)
            )

            if (index < positions.size) {
                val (alignment, offset) = positions[index]
                val letter = letters[index % letters.size]

                Text(
                    text = letter,
                    style = MaterialTheme.typography.displayLarge.copy(
                        fontSize = (24 + (index % 8) * 4).sp,
                        fontWeight = if (index % 3 == 0) FontWeight.Bold else FontWeight.Normal
                    ),
                    color = when (index % 4) {
                        0 -> TypeRushColors.Primary.copy(alpha = alpha)
                        1 -> TypeRushColors.Secondary.copy(alpha = alpha)
                        2 -> TypeRushColors.TextSecondary.copy(alpha = alpha * 0.8f)
                        else -> TypeRushColors.TextPrimary.copy(alpha = alpha * 0.6f)
                    },
                    modifier = Modifier
                        .align(alignment)
                        .offset(x = offset.first, y = offset.second + offsetY.dp)
                        .scale(scale)
                        .rotate(rotation + (index * 5f - 45f)) // Base rotation + animated rotation
                        .alpha(alpha)
                )
            }
        }
    }
}