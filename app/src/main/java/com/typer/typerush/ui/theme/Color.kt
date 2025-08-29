package com.typer.typerush.ui.theme

import androidx.compose.ui.graphics.Color

// Primary Brand Colors
object TypeRushColors {
    // Primary Purple Gradient
    val Primary = Color(0xFF6C63FF)
    val PrimaryVariant = Color(0xFF5A52E8)
    val PrimaryDark = Color(0xFF4B44D1)
    val PrimaryLight = Color(0xFF8B85FF)

    // Secondary Coral/Pink Accent
    val Secondary = Color(0xFFFF6B6B)
    val SecondaryVariant = Color(0xFFE85A5A)
    val SecondaryDark = Color(0xFFD14B4B)
    val SecondaryLight = Color(0xFFFF8B8B)

    // Background Gradient Colors
    val BackgroundPrimary = Color(0xFF1A1A2E)      // Deep navy
    val BackgroundSecondary = Color(0xFF16213E)    // Medium navy
    val BackgroundTertiary = Color(0xFF0F2027)     // Darkest navy

    // Surface Colors (Glassmorphism)
    val SurfaceGlass = Color(0x1AFFFFFF)           // 10% white overlay
    val SurfaceGlassLight = Color(0x26FFFFFF)      // 15% white overlay
    val SurfaceGlassDark = Color(0x0DFFFFFF)       // 5% white overlay
    val SurfaceBlur = Color(0x33FFFFFF)            // 20% white for blur effects

    // Text Colors
    val TextPrimary = Color(0xFFFFFFFF)            // Pure white
    val TextSecondary = Color(0xD9FFFFFF)          // 85% white
    val TextTertiary = Color(0xB3FFFFFF)           // 70% white
    val TextDisabled = Color(0x66FFFFFF)           // 40% white

    // Success, Warning, Error Colors
    val Success = Color(0xFF4CAF50)
    val SuccessLight = Color(0xFF81C784)
    val Warning = Color(0xFFFFC107)
    val WarningLight = Color(0xFFFFD54F)
    val Error = Color(0xFFE53E3E)
    val ErrorLight = Color(0xFFEF5350)

    // Typing Game Specific Colors
    val CorrectText = Color(0xFF4CAF50)            // Green for correct typing
    val IncorrectText = Color(0xFFFF6B6B)          // Coral for incorrect typing
    val CurrentText = Color(0xFF6C63FF)            // Primary for current character
    val UnTypedText = Color(0x80FFFFFF)            // 50% white for untyped text

    // Speed/Performance Indicators
    val SpeedSlow = Color(0xFFFF6B6B)              // Red for slow typing
    val SpeedMedium = Color(0xFFFFC107)            // Yellow for medium typing
    val SpeedFast = Color(0xFF4CAF50)              // Green for fast typing
    val SpeedExpert = Color(0xFF6C63FF)            // Purple for expert typing

    // Leaderboard Ranks
    val RankGold = Color(0xFFFFD700)
    val RankSilver = Color(0xFFC0C0C0)
    val RankBronze = Color(0xFFCD7F32)
    val RankDiamond = Color(0xFF00CED1)

    // Interactive Elements
    val ButtonGlass = Color(0x33FFFFFF)            // 20% white for glass buttons
    val ButtonGlassPressed = Color(0x4DFFFFFF)     // 30% white for pressed state
    val ButtonGlassDisabled = Color(0x1AFFFFFF)    // 10% white for disabled state

    // Overlay Colors
    val OverlayLight = Color(0x40000000)           // 25% black overlay
    val OverlayMedium = Color(0x80000000)          // 50% black overlay
    val OverlayDark = Color(0xCC000000)            // 80% black overlay

    // Animated Background Elements
    val AnimatedPurple = Color(0x1A6C63FF)         // 10% primary purple
    val AnimatedCoral = Color(0x1AFF6B6B)          // 10% secondary coral
    val AnimatedBlue = Color(0x1A00CED1)           // 10% cyan accent

    // Card Colors
    val CardGlass = Color(0x1AFFFFFF)              // Standard glass card
    val CardGlassElevated = Color(0x26FFFFFF)      // Elevated glass card
    val CardGlassPressed = Color(0x33FFFFFF)       // Pressed glass card

    // Border Colors
    val BorderGlass = Color(0x33FFFFFF)            // 20% white border
    val BorderAccent = Color(0xFF6C63FF)           // Primary border
    val BorderError = Color(0xFFFF6B6B)            // Error border

    // Loading/Progress Colors
    val LoadingPrimary = Color(0xFF6C63FF)
    val LoadingSecondary = Color(0xFFFF6B6B)
    val LoadingBackground = Color(0x1AFFFFFF)

    // Navigation Colors
    val NavSelected = Color(0xFF6C63FF)
    val NavUnselected = Color(0x66FFFFFF)
    val NavBackground = Color(0x0D000000)
}

// Light theme colors (if needed for future light mode support)
object TypeRushLightColors {
    val Primary = Color(0xFF6C63FF)
    val PrimaryVariant = Color(0xFF5A52E8)
    val Secondary = Color(0xFFFF6B6B)
    val SecondaryVariant = Color(0xFFE85A5A)

    val Background = Color(0xFFF8F9FA)
    val Surface = Color(0xFFFFFFFF)
    val SurfaceVariant = Color(0xFFF1F3F4)

    val OnPrimary = Color(0xFFFFFFFF)
    val OnSecondary = Color(0xFFFFFFFF)
    val OnBackground = Color(0xFF1A1A2E)
    val OnSurface = Color(0xFF1A1A2E)
    val OnSurfaceVariant = Color(0xFF5F6368)
}

// Gradient definitions
object TypeRushGradients {
    val PrimaryGradient = listOf(
        TypeRushColors.Primary,
        TypeRushColors.PrimaryVariant
    )

    val SecondaryGradient = listOf(
        TypeRushColors.Secondary,
        TypeRushColors.SecondaryVariant
    )

    val BackgroundGradient = listOf(
        TypeRushColors.BackgroundPrimary,
        TypeRushColors.BackgroundSecondary,
        TypeRushColors.BackgroundTertiary
    )

    val RadialBackgroundGradient = listOf(
        TypeRushColors.BackgroundPrimary,
        TypeRushColors.BackgroundSecondary,
        TypeRushColors.BackgroundTertiary
    )

    val GlassGradient = listOf(
        Color(0x26FFFFFF), // 15% white
        Color(0x0DFFFFFF)  // 5% white
    )

    val SpeedGradient = listOf(
        TypeRushColors.SpeedSlow,
        TypeRushColors.SpeedMedium,
        TypeRushColors.SpeedFast,
        TypeRushColors.SpeedExpert
    )

    val SuccessGradient = listOf(
        TypeRushColors.Success,
        TypeRushColors.SuccessLight
    )

    val ErrorGradient = listOf(
        TypeRushColors.Error,
        TypeRushColors.ErrorLight
    )
}