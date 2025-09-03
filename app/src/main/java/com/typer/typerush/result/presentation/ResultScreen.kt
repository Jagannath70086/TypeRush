package com.typer.typerush.result.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.MilitaryTech
import androidx.compose.material.icons.filled.Numbers
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.typer.typerush.core.CurrentGameProvider
import com.typer.typerush.navigation.NavigationManager
import com.typer.typerush.navigation.Screen
import kotlinx.coroutines.runBlocking
import org.koin.compose.koinInject
import java.util.Locale
import kotlin.random.Random

@Composable
fun ResultScreen(
    modifier: Modifier = Modifier,
    currentGameProvider: CurrentGameProvider = koinInject(),
    navigationManager: NavigationManager = koinInject()
) {
    val submission = currentGameProvider.currentGame.collectAsState().value!!.result!!

    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(top = 40.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.EmojiEvents,
                        contentDescription = "Trophy",
                        modifier = Modifier.size(36.dp),
                        tint = Color(0xFFFFD700)
                    )
                    Text(
                        text = "Your Results",
                        style = MaterialTheme.typography.headlineLarge.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 32.sp,
                            brush = Brush.linearGradient(
                                colors = listOf(
                                    Color(0xFFFFD700),
                                    Color(0xFFFFA500),
                                    Color(0xFFFF6B6B)
                                )
                            )
                        )
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    EnhancedResultCard(
                        title = "WPM",
                        value = submission.wpm.toString(),
                        icon = Icons.Default.Speed,
                        gradientColors = listOf(
                            Color(0xFF00D4FF).copy(alpha = 0.8f),
                            Color(0xFF0099CC).copy(alpha = 0.6f)
                        ),
                        modifier = Modifier.weight(1f)
                    )
                    EnhancedResultCard(
                        title = "Accuracy",
                        value = "${String.format(Locale.getDefault(), "%.1f", submission.accuracy)}%",
                        icon = Icons.Default.Numbers,
                        gradientColors = listOf(
                            Color(0xFF4ECDC4).copy(alpha = 0.8f),
                            Color(0xFF44A08D).copy(alpha = 0.6f)
                        ),
                        modifier = Modifier.weight(1f)
                    )
                }

                PerformanceBadge(wpm = submission.wpm, accuracy = submission.accuracy)
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val percentile = Random.nextInt(50, 95)
                Text(
                    text = "🔥 You scored better than $percentile% of players!",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 18.sp,
                        color = Color.White
                    )
                )

                Text(
                    text = "Keep practicing to boost your speed & accuracy 🚀",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color.White.copy(alpha = 0.8f)
                    )
                )

                Button(
                    onClick = { runBlocking { navigationManager.navigateToAndPopBackStack(Screen.TypeTest.route + "/practice", 1) } },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = MaterialTheme.shapes.extraLarge
                ) {

                        Text(
                            text = "Play Again",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }

            }
        }
    }
}

@Composable
private fun EnhancedResultCard(
    title: String,
    value: String,
    icon: ImageVector,
    gradientColors: List<Color>,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .height(180.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.White.copy(alpha = 0.1f),
                            Color.White.copy(alpha = 0.05f)
                        )
                    )
                )
                .background(
                    brush = Brush.verticalGradient(gradientColors),
                    alpha = 0.3f
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    modifier = Modifier.size(32.dp),
                    tint = Color.White.copy(alpha = 0.9f)
                )

                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White.copy(alpha = 0.8f)
                    )
                )

                Text(
                    text = value,
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 28.sp,
                        color = Color.White
                    )
                )
            }
        }
    }
}

@Composable
private fun PerformanceBadge(wpm: Int, accuracy: Double) {
    val (badgeText, badgeColor, badgeIcon) = when {
        wpm >= 80 && accuracy >= 95 -> Triple("Master Typist", Color(0xFFFFD700), Icons.Default.Star)
        wpm >= 60 && accuracy >= 90 -> Triple("Speed Demon", Color(0xFF00D4FF), Icons.Default.Speed)
        wpm >= 40 && accuracy >= 85 -> Triple("Skilled Typist", Color(0xFF4ECDC4), Icons.Default.MilitaryTech)
        else -> Triple("Keep Practicing", Color(0xFF667eea), Icons.Default.EmojiEvents)
    }

    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        ),
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        badgeColor.copy(alpha = 0.2f),
                        badgeColor.copy(alpha = 0.1f)
                    )
                )
            )
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                imageVector = badgeIcon,
                contentDescription = badgeText,
                modifier = Modifier.size(20.dp),
                tint = badgeColor
            )
            Text(
                text = badgeText,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = badgeColor
                )
            )
        }
    }
}