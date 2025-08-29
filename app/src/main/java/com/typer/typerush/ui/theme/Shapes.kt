package com.typer.typerush.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

// TypeRush Shape System - Modern glassmorphic shapes
val TypeRushShapes = Shapes(
    // Small components (chips, small buttons)
    extraSmall = RoundedCornerShape(8.dp),

    // Medium components (cards, dialogs)
    small = RoundedCornerShape(12.dp),

    // Standard components (most cards and buttons)
    medium = RoundedCornerShape(16.dp),

    // Large components (major cards, sheets)
    large = RoundedCornerShape(24.dp),

    // Extra large components (full-screen modals)
    extraLarge = RoundedCornerShape(32.dp)
)

// Custom shapes for specific TypeRush components
object TypeRushCustomShapes {
    // Glassmorphic card shapes
    val glassCardSmall = RoundedCornerShape(16.dp)
    val glassCardMedium = RoundedCornerShape(20.dp)
    val glassCardLarge = RoundedCornerShape(28.dp)
    val glassCardXLarge = RoundedCornerShape(32.dp)

    // Button shapes
    val buttonSmall = RoundedCornerShape(12.dp)
    val buttonMedium = RoundedCornerShape(16.dp)
    val buttonLarge = RoundedCornerShape(20.dp)
    val buttonPill = RoundedCornerShape(50.dp)

    // Input field shapes
    val inputFieldSmall = RoundedCornerShape(8.dp)
    val inputFieldMedium = RoundedCornerShape(12.dp)
    val inputFieldLarge = RoundedCornerShape(16.dp)

    // Modal and dialog shapes
    val modalSmall = RoundedCornerShape(20.dp)
    val modalMedium = RoundedCornerShape(24.dp)
    val modalLarge = RoundedCornerShape(28.dp)

    // Navigation shapes
    val navigationBar = RoundedCornerShape(
        topStart = 20.dp,
        topEnd = 20.dp,
        bottomStart = 0.dp,
        bottomEnd = 0.dp
    )

    val navigationRail = RoundedCornerShape(
        topStart = 0.dp,
        topEnd = 20.dp,
        bottomStart = 0.dp,
        bottomEnd = 20.dp
    )

    // Typing game specific shapes
    val typingBox = RoundedCornerShape(16.dp)
    val typingCard = RoundedCornerShape(20.dp)
    val keyboardKey = RoundedCornerShape(8.dp)

    // Progress indicators
    val progressBarSmall = RoundedCornerShape(4.dp)
    val progressBarMedium = RoundedCornerShape(8.dp)
    val progressBarLarge = RoundedCornerShape(12.dp)

    // Leaderboard shapes
    val leaderboardCard = RoundedCornerShape(18.dp)
    val leaderboardRankBadge = RoundedCornerShape(12.dp)

    // Statistics card shapes
    val statisticsCard = RoundedCornerShape(20.dp)
    val statisticsChip = RoundedCornerShape(16.dp)

    // Profile and user shapes
    val profileCard = RoundedCornerShape(24.dp)
    val avatarSmall = RoundedCornerShape(8.dp)
    val avatarMedium = RoundedCornerShape(12.dp)
    val avatarLarge = RoundedCornerShape(16.dp)

    // Speed indicator shapes
    val speedMeter = RoundedCornerShape(16.dp)
    val speedBadge = RoundedCornerShape(20.dp)

    // Toast and snackbar shapes
    val toast = RoundedCornerShape(12.dp)
    val snackbar = RoundedCornerShape(16.dp)

    // Bottom sheet shapes
    val bottomSheetSmall = RoundedCornerShape(
        topStart = 20.dp,
        topEnd = 20.dp,
        bottomStart = 0.dp,
        bottomEnd = 0.dp
    )

    val bottomSheetMedium = RoundedCornerShape(
        topStart = 24.dp,
        topEnd = 24.dp,
        bottomStart = 0.dp,
        bottomEnd = 0.dp
    )

    val bottomSheetLarge = RoundedCornerShape(
        topStart = 28.dp,
        topEnd = 28.dp,
        bottomStart = 0.dp,
        bottomEnd = 0.dp
    )

    // Tab shapes
    val tabSmall = RoundedCornerShape(12.dp)
    val tabMedium = RoundedCornerShape(16.dp)
    val tabLarge = RoundedCornerShape(20.dp)

    // Search bar shapes
    val searchBarSmall = RoundedCornerShape(20.dp)
    val searchBarMedium = RoundedCornerShape(24.dp)
    val searchBarLarge = RoundedCornerShape(28.dp)

    // Game mode card shapes
    val gameModeCard = RoundedCornerShape(18.dp)
    val gameModeBadge = RoundedCornerShape(14.dp)

    // Achievement shapes
    val achievementCard = RoundedCornerShape(16.dp)
    val achievementBadge = RoundedCornerShape(50.dp) // Circular

    // Settings shapes
    val settingsCard = RoundedCornerShape(16.dp)
    val settingsSection = RoundedCornerShape(20.dp)
}

// Shape theme extensions for easy access
@Composable
fun getGlassShapes() = GlassShapes(
    small = TypeRushCustomShapes.glassCardSmall,
    medium = TypeRushCustomShapes.glassCardMedium,
    large = TypeRushCustomShapes.glassCardLarge,
    extraLarge = TypeRushCustomShapes.glassCardXLarge
)

@Composable
fun getButtonShapes() = ButtonShapes(
    small = TypeRushCustomShapes.buttonSmall,
    medium = TypeRushCustomShapes.buttonMedium,
    large = TypeRushCustomShapes.buttonLarge,
    pill = TypeRushCustomShapes.buttonPill
)

@Composable
fun getInputShapes() = InputShapes(
    small = TypeRushCustomShapes.inputFieldSmall,
    medium = TypeRushCustomShapes.inputFieldMedium,
    large = TypeRushCustomShapes.inputFieldLarge
)

@Composable
fun getModalShapes() = ModalShapes(
    small = TypeRushCustomShapes.modalSmall,
    medium = TypeRushCustomShapes.modalMedium,
    large = TypeRushCustomShapes.modalLarge
)

@Composable
fun getNavigationShapes() = NavigationShapes(
    bar = TypeRushCustomShapes.navigationBar,
    rail = TypeRushCustomShapes.navigationRail
)

@Composable
fun getTypingGameShapes() = TypingGameShapes(
    box = TypeRushCustomShapes.typingBox,
    card = TypeRushCustomShapes.typingCard,
    key = TypeRushCustomShapes.keyboardKey
)

@Composable
fun getProgressShapes() = ProgressShapes(
    small = TypeRushCustomShapes.progressBarSmall,
    medium = TypeRushCustomShapes.progressBarMedium,
    large = TypeRushCustomShapes.progressBarLarge
)

@Composable
fun getLeaderboardShapes() = LeaderboardShapes(
    card = TypeRushCustomShapes.leaderboardCard,
    badge = TypeRushCustomShapes.leaderboardRankBadge
)

@Composable
fun getStatisticsShapes() = StatisticsShapes(
    card = TypeRushCustomShapes.statisticsCard,
    chip = TypeRushCustomShapes.statisticsChip
)

@Composable
fun getBottomSheetShapes() = BottomSheetShapes(
    small = TypeRushCustomShapes.bottomSheetSmall,
    medium = TypeRushCustomShapes.bottomSheetMedium,
    large = TypeRushCustomShapes.bottomSheetLarge
)

// Data classes for organized shape groups
data class GlassShapes(
    val small: RoundedCornerShape,
    val medium: RoundedCornerShape,
    val large: RoundedCornerShape,
    val extraLarge: RoundedCornerShape
)

data class ButtonShapes(
    val small: RoundedCornerShape,
    val medium: RoundedCornerShape,
    val large: RoundedCornerShape,
    val pill: RoundedCornerShape
)

data class InputShapes(
    val small: RoundedCornerShape,
    val medium: RoundedCornerShape,
    val large: RoundedCornerShape
)

data class ModalShapes(
    val small: RoundedCornerShape,
    val medium: RoundedCornerShape,
    val large: RoundedCornerShape
)

data class NavigationShapes(
    val bar: RoundedCornerShape,
    val rail: RoundedCornerShape
)

data class TypingGameShapes(
    val box: RoundedCornerShape,
    val card: RoundedCornerShape,
    val key: RoundedCornerShape
)

data class ProgressShapes(
    val small: RoundedCornerShape,
    val medium: RoundedCornerShape,
    val large: RoundedCornerShape
)

data class LeaderboardShapes(
    val card: RoundedCornerShape,
    val badge: RoundedCornerShape
)

data class StatisticsShapes(
    val card: RoundedCornerShape,
    val chip: RoundedCornerShape
)

data class BottomSheetShapes(
    val small: RoundedCornerShape,
    val medium: RoundedCornerShape,
    val large: RoundedCornerShape
)