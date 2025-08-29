package com.typer.typerush.splash

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.typer.typerush.BuildConfig
import com.typer.typerush.splash.components.BackgroundDecorations
import kotlinx.coroutines.delay
import org.koin.compose.viewmodel.koinViewModel
import com.typer.typerush.splash.components.LoadingDots
import com.typer.typerush.R
import com.typer.typerush.ui.theme.TypeRushColors
import com.typer.typerush.ui.theme.TypeRushGradients
import com.typer.typerush.ui.theme.TypeRushTextStyles

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    viewModel: SplashViewModel = koinViewModel()
) {
    var startAnimation by remember { mutableStateOf(false) }
    val state by viewModel.state.collectAsState()

    val logoScale by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0.3f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "logoScale"
    )

    val logoAlpha by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(1000, easing = FastOutSlowInEasing),
        label = "logoAlpha"
    )

    val titleScale by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0.8f,
        animationSpec = tween(800, delayMillis = 300, easing = FastOutSlowInEasing),
        label = "titleScale"
    )

    val titleAlpha by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(800, delayMillis = 400),
        label = "titleAlpha"
    )

    val contentAlpha by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(600, delayMillis = 900),
        label = "contentAlpha"
    )

    val footerAlpha by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(500, delayMillis = 1400),
        label = "footerAlpha"
    )

    LaunchedEffect(Unit) {
        delay(100)
        startAnimation = true
        viewModel.onIntent(SplashIntent.FetchAppInfo)
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.radialGradient(
                    colors = TypeRushGradients.RadialBackgroundGradient,
                    radius = 1000f
                )
            )
    ) {
        BackgroundDecorations(modifier = modifier.alpha(contentAlpha * 0.3f))

        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = modifier.weight(1f))

            Box(
                modifier = modifier
                    .size(100.dp)
                    .scale(logoScale)
                    .alpha(logoAlpha)
                    .clip(RoundedCornerShape(24.dp))
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = modifier
                        .fillMaxSize()
                        .background(TypeRushColors.SurfaceGlass)
                )

                Image(
                    painter = painterResource(id = R.drawable.icon),
                    contentDescription = "TypeRush Logo",
                    modifier = modifier.size(70.dp)
                )
            }

            Spacer(modifier = modifier.height(40.dp))

            Box(
                modifier = modifier
                    .scale(titleScale)
                    .alpha(titleAlpha)
            ) {
                Text(
                    text = "TypeRush",
                    style = TypeRushTextStyles.gameTitle.copy(
                        fontSize = 36.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    color = TypeRushColors.TextPrimary
                )
            }

            Spacer(modifier = modifier.height(8.dp))

            Text(
                text = "Master Your Typing Speed",
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = TypeRushColors.TextSecondary,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                ),
                modifier = modifier.alpha(titleAlpha),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = modifier.height(60.dp))

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifier.alpha(contentAlpha)
            ) {
                LoadingDots()

                Spacer(modifier = modifier.height(24.dp))

                Card(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = TypeRushColors.SurfaceGlass
                    ),
                    elevation = CardDefaults.cardElevation(0.dp),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Box(
                        modifier = modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = state.currentAction?.message ?: "Initializing...",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = TypeRushColors.TextSecondary,
                                fontSize = 14.sp
                            ),
                            textAlign = TextAlign.Center,
                            modifier = modifier.padding(vertical = 16.dp, horizontal = 20.dp),
                        )
                    }
                }
            }

            Spacer(modifier = modifier.weight(1f))

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifier.alpha(footerAlpha)
            ) {
                Text(
                    text = "Made with ❤️ by Jagannath",
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontWeight = FontWeight.Medium,
                        fontSize = 13.sp
                    ),
                    color = TypeRushColors.TextSecondary.copy(alpha = 0.8f)
                )

                Spacer(modifier = modifier.height(8.dp))

                Text(
                    text = "Version ${BuildConfig.VERSION_NAME}",
                    style = MaterialTheme.typography.bodySmall,
                    color = TypeRushColors.TextSecondary.copy(alpha = 0.5f),
                    fontSize = 11.sp
                )
            }

            Spacer(modifier = modifier.height(32.dp))
        }
    }
}

data class Offset(val x: Dp, val y: Dp)