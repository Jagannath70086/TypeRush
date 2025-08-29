package com.typer.typerush.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.unit.sp
import com.typer.typerush.R

// Google Fonts Provider
val provider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)

// Primary font families using Google Fonts
val bodyFontFamily = FontFamily(
    Font(
        googleFont = GoogleFont("Lexend"),
        fontProvider = provider,
    )
)

val displayFontFamily = FontFamily(
    Font(
        googleFont = GoogleFont("Lexend"),
        fontProvider = provider,
        weight = FontWeight.SemiBold
    )
)

// Monospace font for typing game text
val monospaceFontFamily = FontFamily(
    Font(
        googleFont = GoogleFont("JetBrains Mono"),
        fontProvider = provider,
        weight = FontWeight.Normal
    ),
    Font(
        googleFont = GoogleFont("JetBrains Mono"),
        fontProvider = provider,
        weight = FontWeight.Medium
    ),
    Font(
        googleFont = GoogleFont("JetBrains Mono"),
        fontProvider = provider,
        weight = FontWeight.Bold
    )
)

// Material3 Typography baseline
val baseline = Typography()

// TypeRush Typography System using Google Fonts
val TypeRushTypography = Typography(
    // Display styles - for large hero text
    displayLarge = baseline.displayLarge.copy(
        fontFamily = displayFontFamily,
        fontWeight = FontWeight.Bold,
        color = TypeRushColors.TextPrimary
    ),
    displayMedium = baseline.displayMedium.copy(
        fontFamily = displayFontFamily,
        fontWeight = FontWeight.Bold,
        color = TypeRushColors.TextPrimary
    ),
    displaySmall = baseline.displaySmall.copy(
        fontFamily = displayFontFamily,
        fontWeight = FontWeight.SemiBold,
        color = TypeRushColors.TextPrimary
    ),

    // Headline styles - for section headers
    headlineLarge = baseline.headlineLarge.copy(
        fontFamily = displayFontFamily,
        fontWeight = FontWeight.SemiBold,
        color = TypeRushColors.TextPrimary
    ),
    headlineMedium = baseline.headlineMedium.copy(
        fontFamily = displayFontFamily,
        fontWeight = FontWeight.SemiBold,
        color = TypeRushColors.TextPrimary
    ),
    headlineSmall = baseline.headlineSmall.copy(
        fontFamily = displayFontFamily,
        fontWeight = FontWeight.Medium,
        color = TypeRushColors.TextPrimary
    ),

    // Title styles - for card titles and important text
    titleLarge = baseline.titleLarge.copy(
        fontFamily = displayFontFamily,
        fontWeight = FontWeight.SemiBold,
        color = TypeRushColors.TextPrimary
    ),
    titleMedium = baseline.titleMedium.copy(
        fontFamily = displayFontFamily,
        fontWeight = FontWeight.Medium,
        color = TypeRushColors.TextPrimary
    ),
    titleSmall = baseline.titleSmall.copy(
        fontFamily = displayFontFamily,
        fontWeight = FontWeight.Medium,
        color = TypeRushColors.TextPrimary
    ),

    // Body styles - for regular content
    bodyLarge = baseline.bodyLarge.copy(
        fontFamily = bodyFontFamily,
        color = TypeRushColors.TextSecondary
    ),
    bodyMedium = baseline.bodyMedium.copy(
        fontFamily = bodyFontFamily,
        color = TypeRushColors.TextSecondary
    ),
    bodySmall = baseline.bodySmall.copy(
        fontFamily = bodyFontFamily,
        color = TypeRushColors.TextTertiary
    ),

    // Label styles - for buttons and small UI elements
    labelLarge = baseline.labelLarge.copy(
        fontFamily = bodyFontFamily,
        fontWeight = FontWeight.Medium,
        color = TypeRushColors.TextPrimary
    ),
    labelMedium = baseline.labelMedium.copy(
        fontFamily = bodyFontFamily,
        fontWeight = FontWeight.Medium,
        color = TypeRushColors.TextSecondary
    ),
    labelSmall = baseline.labelSmall.copy(
        fontFamily = bodyFontFamily,
        fontWeight = FontWeight.Medium,
        color = TypeRushColors.TextTertiary
    )
)

// Enhanced TypeRush Custom Typography Styles
object TypeRushTextStyles {
    // Game title - enhanced with TypeRush colors
    val gameTitle = TextStyle(
        fontFamily = displayFontFamily,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 48.sp,
        lineHeight = 45.sp,
        letterSpacing = (-0.5).sp,
        color = TypeRushColors.Primary
    )

    // WPM counter - enhanced with secondary color
    val wpmCounter = TextStyle(
        fontFamily = displayFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp,
        lineHeight = 40.sp,
        letterSpacing = 0.sp,
        color = TypeRushColors.Secondary
    )

    // Typing text - using monospace for better readability
    val typingText = TextStyle(
        fontFamily = monospaceFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.15.sp,
        color = TypeRushColors.TextPrimary
    )

    // Timer - enhanced with primary color
    val timer = TextStyle(
        fontFamily = displayFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.sp,
        color = TypeRushColors.TextPrimary
    )

    // Stats label - with secondary text color
    val statsLabel = TextStyle(
        fontFamily = bodyFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 13.sp,
        lineHeight = 18.sp,
        letterSpacing = 0.3.sp,
        color = TypeRushColors.TextSecondary
    )

    // Button text - enhanced styling
    val buttonText = TextStyle(
        fontFamily = displayFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp,
        color = TypeRushColors.TextPrimary
    )

    // Leaderboard rank - with rank-specific colors
    val leaderboardRank = TextStyle(
        fontFamily = displayFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp,
        color = TypeRushColors.RankGold
    )

    // Typing game text styles with different states
    val typingTextCorrect = typingText.copy(
        color = TypeRushColors.CorrectText
    )

    val typingTextIncorrect = typingText.copy(
        color = TypeRushColors.IncorrectText,
        fontWeight = FontWeight.Medium
    )

    val typingTextCurrent = typingText.copy(
        color = TypeRushColors.CurrentText,
        fontWeight = FontWeight.Medium
    )

    val typingTextUntyped = typingText.copy(
        color = TypeRushColors.UnTypedText
    )

    // Speed indicators
    val speedSlow = TextStyle(
        fontFamily = displayFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.15.sp,
        color = TypeRushColors.SpeedSlow
    )

    val speedMedium = speedSlow.copy(color = TypeRushColors.SpeedMedium)
    val speedFast = speedSlow.copy(color = TypeRushColors.SpeedFast)
    val speedExpert = speedSlow.copy(color = TypeRushColors.SpeedExpert)

    // Statistics styles
    val statisticsLarge = TextStyle(
        fontFamily = displayFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 42.sp,
        lineHeight = 50.sp,
        letterSpacing = (-0.5).sp,
        color = TypeRushColors.Primary
    )

    val statisticsMedium = TextStyle(
        fontFamily = displayFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 28.sp,
        lineHeight = 36.sp,
        letterSpacing = (-0.25).sp,
        color = TypeRushColors.Primary
    )

    val statisticsSmall = TextStyle(
        fontFamily = displayFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 18.sp,
        lineHeight = 26.sp,
        letterSpacing = 0.sp,
        color = TypeRushColors.Primary
    )

    // Timer variants
    val timerLarge = TextStyle(
        fontFamily = monospaceFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp,
        lineHeight = 40.sp,
        letterSpacing = (-0.5).sp,
        color = TypeRushColors.TextPrimary
    )

    val timerMedium = TextStyle(
        fontFamily = monospaceFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 20.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp,
        color = TypeRushColors.TextPrimary
    )

    // Leaderboard styles
    val leaderboardName = TextStyle(
        fontFamily = bodyFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.sp,
        color = TypeRushColors.TextPrimary
    )

    val leaderboardScore = TextStyle(
        fontFamily = monospaceFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.25.sp,
        color = TypeRushColors.TextSecondary
    )

    // Card text styles
    val cardTitle = TextStyle(
        fontFamily = displayFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 18.sp,
        lineHeight = 26.sp,
        letterSpacing = 0.sp,
        color = TypeRushColors.TextPrimary
    )

    val cardSubtitle = TextStyle(
        fontFamily = bodyFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.25.sp,
        color = TypeRushColors.TextSecondary
    )

    val cardBody = TextStyle(
        fontFamily = bodyFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 18.sp,
        letterSpacing = 0.4.sp,
        color = TypeRushColors.TextTertiary
    )
}

// Data classes for organized typography groups
data class TypingTypographyStyles(
    val large: TextStyle,
    val medium: TextStyle,
    val small: TextStyle
)

data class StatisticsTypographyStyles(
    val large: TextStyle,
    val medium: TextStyle,
    val small: TextStyle,
    val speedIndicator: TextStyle
)

data class TimerTypographyStyles(
    val large: TextStyle,
    val medium: TextStyle
)

data class LeaderboardTypographyStyles(
    val rank: TextStyle,
    val name: TextStyle,
    val score: TextStyle
)

data class ButtonTypographyStyles(
    val large: TextStyle,
    val medium: TextStyle,
    val small: TextStyle
)

data class CardTypographyStyles(
    val title: TextStyle,
    val subtitle: TextStyle,
    val body: TextStyle
)

// Typography theme extensions
@Composable
fun getTypingTypography() = TypingTypographyStyles(
    large = TypeRushTextStyles.typingText.copy(fontSize = 20.sp),
    medium = TypeRushTextStyles.typingText,
    small = TypeRushTextStyles.typingText.copy(fontSize = 16.sp)
)

@Composable
fun getStatisticsTypography() = StatisticsTypographyStyles(
    large = TypeRushTextStyles.statisticsLarge,
    medium = TypeRushTextStyles.statisticsMedium,
    small = TypeRushTextStyles.statisticsSmall,
    speedIndicator = TypeRushTextStyles.speedMedium
)

@Composable
fun getTimerTypography() = TimerTypographyStyles(
    large = TypeRushTextStyles.timerLarge,
    medium = TypeRushTextStyles.timerMedium
)

@Composable
fun getLeaderboardTypography() = LeaderboardTypographyStyles(
    rank = TypeRushTextStyles.leaderboardRank,
    name = TypeRushTextStyles.leaderboardName,
    score = TypeRushTextStyles.leaderboardScore
)

@Composable
fun getButtonTypography() = ButtonTypographyStyles(
    large = TypeRushTextStyles.buttonText.copy(fontSize = 18.sp),
    medium = TypeRushTextStyles.buttonText,
    small = TypeRushTextStyles.buttonText.copy(fontSize = 14.sp)
)

@Composable
fun getCardTypography() = CardTypographyStyles(
    title = TypeRushTextStyles.cardTitle,
    subtitle = TypeRushTextStyles.cardSubtitle,
    body = TypeRushTextStyles.cardBody
)

// Preview helpers and utility functions
@Composable
fun TypeRushTypographyPreview(
    content: @Composable () -> Unit
) {
    TypeRushTheme {
        content()
    }
}

// Extension functions for easy text styling
fun TextStyle.withTypingState(isCorrect: Boolean?, isCurrent: Boolean = false): TextStyle {
    return when {
        isCurrent -> this.copy(color = TypeRushColors.CurrentText, fontWeight = FontWeight.Medium)
        isCorrect == true -> this.copy(color = TypeRushColors.CorrectText)
        isCorrect == false -> this.copy(color = TypeRushColors.IncorrectText, fontWeight = FontWeight.Medium)
        else -> this.copy(color = TypeRushColors.UnTypedText)
    }
}

fun TextStyle.withSpeedColor(wpm: Int): TextStyle {
    val color = when {
        wpm < 30 -> TypeRushColors.SpeedSlow
        wpm < 50 -> TypeRushColors.SpeedMedium
        wpm < 80 -> TypeRushColors.SpeedFast
        else -> TypeRushColors.SpeedExpert
    }
    return this.copy(color = color)
}

fun TextStyle.withRankColor(rank: Int): TextStyle {
    val color = when (rank) {
        1 -> TypeRushColors.RankGold
        2 -> TypeRushColors.RankSilver
        3 -> TypeRushColors.RankBronze
        else -> if (rank <= 10) TypeRushColors.RankDiamond else TypeRushColors.TextSecondary
    }
    return this.copy(color = color)
}