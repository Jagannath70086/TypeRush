package com.typer.typerush.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

// TypeRush Dark Color Scheme
private val TypeRushDarkColorScheme = darkColorScheme(
    // Primary colors
    primary = TypeRushColors.Primary,
    onPrimary = Color.White,
    primaryContainer = TypeRushColors.PrimaryDark,
    onPrimaryContainer = Color.White,

    // Secondary colors
    secondary = TypeRushColors.Secondary,
    onSecondary = Color.White,
    secondaryContainer = TypeRushColors.SecondaryDark,
    onSecondaryContainer = Color.White,

    // Tertiary colors
    tertiary = Color(0xFF00CED1), // Cyan accent
    onTertiary = Color.White,
    tertiaryContainer = Color(0xFF008B8B),
    onTertiaryContainer = Color.White,

    // Background colors
    background = TypeRushColors.BackgroundPrimary,
    onBackground = TypeRushColors.TextPrimary,

    // Surface colors (glassmorphic)
    surface = TypeRushColors.SurfaceGlass,
    onSurface = TypeRushColors.TextPrimary,
    surfaceVariant = TypeRushColors.SurfaceGlassLight,
    onSurfaceVariant = TypeRushColors.TextSecondary,

    // Inverse colors
    inverseSurface = Color.White,
    inverseOnSurface = TypeRushColors.BackgroundPrimary,
    inversePrimary = TypeRushColors.Primary,

    // Outline colors
    outline = TypeRushColors.BorderGlass,
    outlineVariant = TypeRushColors.BorderGlass.copy(alpha = 0.5f),

    // Container colors
    surfaceTint = TypeRushColors.Primary,
    surfaceContainer = TypeRushColors.SurfaceGlass,
    surfaceContainerHigh = TypeRushColors.SurfaceGlassLight,
    surfaceContainerHighest = TypeRushColors.SurfaceBlur,
    surfaceContainerLow = TypeRushColors.SurfaceGlassDark,
    surfaceContainerLowest = TypeRushColors.BackgroundTertiary,

    // Utility colors
    error = TypeRushColors.Error,
    onError = Color.White,
    errorContainer = TypeRushColors.ErrorLight,
    onErrorContainer = Color.White,

    // Scrim
    scrim = TypeRushColors.OverlayDark
)

// TypeRush Light Color Scheme (for future use)
private val TypeRushLightColorScheme = lightColorScheme(
    primary = TypeRushLightColors.Primary,
    onPrimary = TypeRushLightColors.OnPrimary,
    primaryContainer = TypeRushLightColors.PrimaryVariant,
    onPrimaryContainer = Color.White,

    secondary = TypeRushLightColors.Secondary,
    onSecondary = TypeRushLightColors.OnSecondary,
    secondaryContainer = TypeRushLightColors.SecondaryVariant,
    onSecondaryContainer = Color.White,

    tertiary = Color(0xFF00ACC1),
    onTertiary = Color.White,
    tertiaryContainer = Color(0xFF4DD0E1),
    onTertiaryContainer = Color.Black,

    background = TypeRushLightColors.Background,
    onBackground = TypeRushLightColors.OnBackground,

    surface = TypeRushLightColors.Surface,
    onSurface = TypeRushLightColors.OnSurface,
    surfaceVariant = TypeRushLightColors.SurfaceVariant,
    onSurfaceVariant = TypeRushLightColors.OnSurfaceVariant,

    inverseSurface = TypeRushColors.BackgroundPrimary,
    inverseOnSurface = Color.White,
    inversePrimary = TypeRushColors.Primary,

    outline = Color(0xFFDDDDDD),
    outlineVariant = Color(0xFFEEEEEE),

    surfaceTint = TypeRushLightColors.Primary,

    error = TypeRushColors.Error,
    onError = Color.White,
    errorContainer = Color(0xFFFFEBEE),
    onErrorContainer = TypeRushColors.Error,

    scrim = Color.Black.copy(alpha = 0.32f)
)

@Composable
fun TypeRushTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> TypeRushDarkColorScheme
        else -> TypeRushLightColorScheme
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            window.navigationBarColor = colorScheme.background.toArgb()

            WindowCompat.getInsetsController(window, view).apply {
                isAppearanceLightStatusBars = !darkTheme
                isAppearanceLightNavigationBars = !darkTheme
            }
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = TypeRushTypography,
        shapes = TypeRushShapes,
        content = content
    )
}

// Custom color extensions for easy access
@Composable
fun getTypingColors() = TypingColors(
    correct = TypeRushColors.CorrectText,
    incorrect = TypeRushColors.IncorrectText,
    current = TypeRushColors.CurrentText,
    untyped = TypeRushColors.UnTypedText
)

@Composable
fun getSpeedColors() = SpeedColors(
    slow = TypeRushColors.SpeedSlow,
    medium = TypeRushColors.SpeedMedium,
    fast = TypeRushColors.SpeedFast,
    expert = TypeRushColors.SpeedExpert
)

@Composable
fun getRankColors() = RankColors(
    gold = TypeRushColors.RankGold,
    silver = TypeRushColors.RankSilver,
    bronze = TypeRushColors.RankBronze,
    diamond = TypeRushColors.RankDiamond
)

@Composable
fun getGlassColors() = GlassColors(
    surface = TypeRushColors.SurfaceGlass,
    surfaceLight = TypeRushColors.SurfaceGlassLight,
    surfaceDark = TypeRushColors.SurfaceGlassDark,
    blur = TypeRushColors.SurfaceBlur,
    button = TypeRushColors.ButtonGlass,
    buttonPressed = TypeRushColors.ButtonGlassPressed,
    buttonDisabled = TypeRushColors.ButtonGlassDisabled
)

// Data classes for organized color groups
data class TypingColors(
    val correct: Color,
    val incorrect: Color,
    val current: Color,
    val untyped: Color
)

data class SpeedColors(
    val slow: Color,
    val medium: Color,
    val fast: Color,
    val expert: Color
)

data class RankColors(
    val gold: Color,
    val silver: Color,
    val bronze: Color,
    val diamond: Color
)

data class GlassColors(
    val surface: Color,
    val surfaceLight: Color,
    val surfaceDark: Color,
    val blur: Color,
    val button: Color,
    val buttonPressed: Color,
    val buttonDisabled: Color
)

// Theme-aware color accessors
object TypeRushThemeColors {
    val primary: Color
        @Composable get() = MaterialTheme.colorScheme.primary

    val secondary: Color
        @Composable get() = MaterialTheme.colorScheme.secondary

    val background: Color
        @Composable get() = MaterialTheme.colorScheme.background

    val surface: Color
        @Composable get() = MaterialTheme.colorScheme.surface

    val onPrimary: Color
        @Composable get() = MaterialTheme.colorScheme.onPrimary

    val onSecondary: Color
        @Composable get() = MaterialTheme.colorScheme.onSecondary

    val onBackground: Color
        @Composable get() = MaterialTheme.colorScheme.onBackground

    val onSurface: Color
        @Composable get() = MaterialTheme.colorScheme.onSurface

    val error: Color
        @Composable get() = MaterialTheme.colorScheme.error

    val onError: Color
        @Composable get() = MaterialTheme.colorScheme.onError
}

@Composable
fun TypeRushPreviewTheme(
    content: @Composable () -> Unit
) {
    TypeRushTheme(
        darkTheme = true,
        dynamicColor = false,
        content = content
    )
}