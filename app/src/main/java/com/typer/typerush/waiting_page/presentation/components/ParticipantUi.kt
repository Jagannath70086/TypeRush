package com.typer.typerush.waiting_page.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.typer.typerush.ui.theme.TypeRushColors

@Composable
fun ParticipantUi(
    number: Int,
    name: String,
    isCreator: Boolean,
    wpm: Int,
    accuracy: Double,
    hasFinished: Boolean
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (hasFinished) {
                Color(0xFF66BB6A).copy(alpha = 0.05f)
            } else {
                MaterialTheme.colorScheme.surface
            }
        ),
        border = BorderStroke(
            width = 1.dp,
            color = if (hasFinished) {
                Color(0xFF66BB6A).copy(alpha = 0.3f)
            } else if (isCreator) {
                Color(0xFFFFD700).copy(alpha = 0.4f)
            } else {
                TypeRushColors.Primary.copy(alpha = 0.2f)
            }
        ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Left side - Position and Name
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Position Number
                Surface(
                    shape = CircleShape,
                    color = when {
                        hasFinished && number == 1 -> Color(0xFFFFD700).copy(alpha = 0.2f)
                        hasFinished && number == 2 -> Color(0xFFC0C0C0).copy(alpha = 0.2f)
                        hasFinished && number == 3 -> Color(0xFFCD7F32).copy(alpha = 0.2f)
                        hasFinished -> Color(0xFF66BB6A).copy(alpha = 0.1f)
                        else -> TypeRushColors.Primary.copy(alpha = 0.1f)
                    },
                    border = BorderStroke(
                        1.dp,
                        when {
                            hasFinished && number == 1 -> Color(0xFFFFD700)
                            hasFinished && number == 2 -> Color(0xFFC0C0C0)
                            hasFinished && number == 3 -> Color(0xFFCD7F32)
                            hasFinished -> Color(0xFF66BB6A)
                            else -> TypeRushColors.Primary.copy(alpha = 0.3f)
                        }
                    )
                ) {
                    Box(
                        modifier = Modifier.size(32.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        if (hasFinished && number <= 3) {
                            Icon(
                                imageVector = when (number) {
                                    1 -> Icons.Default.EmojiEvents
                                    2 -> Icons.Default.WorkspacePremium
                                    3 -> Icons.Default.Workspaces
                                    else -> Icons.Default.CheckCircle
                                },
                                contentDescription = null,
                                modifier = Modifier.size(16.dp),
                                tint = when (number) {
                                    1 -> Color(0xFFFFD700)
                                    2 -> Color(0xFFC0C0C0)
                                    3 -> Color(0xFFCD7F32)
                                    else -> Color(0xFF66BB6A)
                                }
                            )
                        } else {
                            Text(
                                text = number.toString(),
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontWeight = FontWeight.Bold
                                ),
                                color = if (hasFinished) Color(0xFF66BB6A) else TypeRushColors.Primary
                            )
                        }
                    }
                }

                // Name and Creator Badge
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(
                            text = name,
                            modifier = Modifier.weight(1f, fill = false),
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontWeight = FontWeight.SemiBold
                            ),
                            color = TypeRushColors.TextPrimary,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )

                        if (isCreator) {
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = Color(0xFFFFD700).copy(alpha = 0.15f),
                                border = BorderStroke(
                                    0.5.dp,
                                    Color(0xFFFFD700).copy(alpha = 0.4f)
                                )
                            ) {
                                Text(
                                    text = "HOST",
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontWeight = FontWeight.Bold,
                                        letterSpacing = 0.5.sp
                                    ),
                                    color = Color(0xFFFFD700)
                                )
                            }
                        }
                    }

                    if (hasFinished) {
                        Text(
                            text = "Finished!",
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontWeight = FontWeight.Medium
                            ),
                            color = Color(0xFF66BB6A)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Right side - Stats
            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                // WPM and Accuracy
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Speed,
                        contentDescription = null,
                        modifier = Modifier.size(14.dp),
                        tint = TypeRushColors.Primary.copy(alpha = 0.7f)
                    )
                    Text(
                        text = "$wpm",
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        color = TypeRushColors.TextPrimary
                    )
                    Text(
                        text = "WPM",
                        style = MaterialTheme.typography.bodySmall,
                        color = TypeRushColors.TextPrimary.copy(alpha = 0.6f)
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Verified,
                        contentDescription = null,
                        modifier = Modifier.size(14.dp),
                        tint = when {
                            accuracy >= 95 -> Color(0xFF66BB6A)
                            accuracy >= 85 -> Color(0xFFFFA726)
                            else -> Color(0xFFEF5350)
                        }.copy(alpha = 0.8f)
                    )
                    Text(
                        text = "${accuracy.toInt()}%",
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        color = when {
                            accuracy >= 95 -> Color(0xFF66BB6A)
                            accuracy >= 85 -> Color(0xFFFFA726)
                            else -> Color(0xFFEF5350)
                        }
                    )
                    Text(
                        text = "Acc",
                        style = MaterialTheme.typography.bodySmall,
                        color = TypeRushColors.TextPrimary.copy(alpha = 0.6f)
                    )
                }
            }
        }
    }
}