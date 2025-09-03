package com.typer.typerush.waiting_page.presentation.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.HourglassEmpty
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.typer.typerush.ui.theme.TypeRushColors
import com.typer.typerush.ui.theme.TypeRushTextStyles

@Composable
fun StatusCard(
    status: String,
    modifier: Modifier = Modifier
) {
    val (colors, icon, iconTint, message) = when (status.uppercase()) {
        "WAITING" -> Quadruple(
            listOf(
                Color(0xFFFFC107).copy(alpha = 0.3f),
                Color(0xFFFFD54F).copy(alpha = 0.3f),
                Color(0xFFFFA726).copy(alpha = 0.3f)
            ),
            Icons.Default.HourglassEmpty,
            Color(0xFFFFC107),
            "Waiting for the host to start"
        )
        "FINISHED" -> Quadruple(
            listOf(
                Color(0xFF2DC286).copy(alpha = 0.3f),
                Color(0xFF2363C4).copy(alpha = 0.3f),
                Color(0xFF3F1AE3).copy(alpha = 0.3f)
            ),
            Icons.Default.CheckCircle,
            Color(0xFF5CEA3C),
            "Contest has finished"
        )
        else -> Quadruple(
            listOf(
                MaterialTheme.colorScheme.primary.copy(alpha = 0.3f),
                MaterialTheme.colorScheme.secondary.copy(alpha = 0.3f)
            ),
            Icons.Default.Info,
            MaterialTheme.colorScheme.primary,
            status
        )
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp)
            .animateContentSize(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        border = BorderStroke(
            width = 1.dp,
            brush = Brush.linearGradient(colors = colors)
        )
    ) {
        Row(
            modifier = modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = status,
                modifier = Modifier.size(24.dp),
                tint = iconTint
            )
            Text(
                text = message,
                style = TypeRushTextStyles.buttonText.copy(
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                ),
                color = TypeRushColors.TextPrimary
            )
        }
    }
}

data class Quadruple(
    val first: List<Color>,
    val second: ImageVector,
    val third: Color,
    val fourth: String
)
