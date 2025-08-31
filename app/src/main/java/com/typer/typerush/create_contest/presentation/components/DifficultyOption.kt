package com.typer.typerush.create_contest.presentation.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.typer.typerush.ui.theme.TypeRushColors
import com.typer.typerush.ui.theme.TypeRushGradients

@Composable
fun DifficultyOption(
    text: String,
    emoji: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scale by animateFloatAsState(
        targetValue = if (selected) 1.02f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "optionScale"
    )

    val animatedColor by animateColorAsState(
        targetValue = if (selected) {
            TypeRushColors.Primary.copy(alpha = 0.15f)
        } else {
            Color.White.copy(alpha = 0.05f)
        },
        label = "backgroundColor"
    )

    Box(
        modifier = modifier
            .scale(scale)
            .height(64.dp)
            .clip(RoundedCornerShape(18.dp))
            .background(animatedColor)
            .then(
                if (selected) {
                    Modifier.border(
                        2.dp,
                        Brush.linearGradient(TypeRushGradients.PrimaryGradient),
                        RoundedCornerShape(18.dp)
                    )
                } else {
                    Modifier.border(
                        1.dp,
                        Color.White.copy(alpha = 0.1f),
                        RoundedCornerShape(18.dp)
                    )
                }
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = emoji,
                style = MaterialTheme.typography.titleMedium,
                fontSize = 16.sp
            )
            Text(
                text = text,
                style = MaterialTheme.typography.labelMedium.copy(
                    fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium,
                    fontSize = 13.sp
                ),
                color = if (selected) TypeRushColors.Primary else TypeRushColors.TextSecondary,
                textAlign = TextAlign.Center
            )
        }
    }
}