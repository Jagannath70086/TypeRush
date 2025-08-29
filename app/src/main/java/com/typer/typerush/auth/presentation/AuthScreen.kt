package com.typer.typerush.auth.presentation

import android.content.res.Configuration
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.typer.typerush.R
import com.typer.typerush.auth.presentation.components.BackgroundElements
import com.typer.typerush.auth.presentation.components.SignInButton
import com.typer.typerush.auth.presentation.models.PageData
import com.typer.typerush.core.components.SnackBarType
import com.typer.typerush.core.components.TyperSnackBar
import com.typer.typerush.ui.theme.TypeRushColors
import com.typer.typerush.ui.theme.TypeRushCustomShapes
import com.typer.typerush.ui.theme.TypeRushGradients
import com.typer.typerush.ui.theme.TypeRushTextStyles
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AuthScreen(
    modifier: Modifier = Modifier,
    viewModel: AuthViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()

    val pageData = listOf(
        PageData(
            title = "Welcome to TypeRush",
            description = "Experience the ultimate typing challenge with stunning visuals and smooth gameplay that will revolutionize your typing skills.",
            icon = Icons.Default.Speed
        ),
        PageData(
            title = "Race Against Time",
            description = "Challenge yourself with thrilling timed typing races and beat your personal records in exciting competitions.",
            icon = Icons.Default.Timer
        ),
        PageData(
            title = "Improve Your Skills",
            description = "Track your progress with detailed analytics and unlock new levels of typing mastery with personalized insights.",
            icon = Icons.AutoMirrored.Filled.TrendingUp
        ),
        PageData(
            title = "Compete Globally",
            description = "Join players worldwide in real-time competitions and climb the global leaderboards to prove your typing supremacy.",
            icon = Icons.Default.Public
        )
    )

    val pagerState = rememberPagerState(initialPage = 0, pageCount = { pageData.size })

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.radialGradient(
                    colors = TypeRushGradients.RadialBackgroundGradient,
                    radius = 1200f
                )
            )
    ) {
        BackgroundElements()

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            val config = LocalConfiguration.current
            val isLandscape = config.orientation == Configuration.ORIENTATION_LANDSCAPE
            val currentPageData = pageData[page]

            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(vertical = 32.dp, horizontal = 24.dp),
                verticalArrangement = if (isLandscape) Arrangement.Center else Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = modifier.weight(if (isLandscape) 0.5f else 1f))

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = if (isLandscape) Arrangement.Center else Arrangement.Top
                ) {
                    val scale by animateFloatAsState(
                        targetValue = 1f,
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioMediumBouncy,
                            stiffness = Spring.StiffnessLow
                        ),
                        label = "iconScale"
                    )
                    Box(
                        modifier = modifier
                            .size(100.dp)
                            .scale(scale)
                            .clip(if (page == 0) RoundedCornerShape(24.dp) else TypeRushCustomShapes.avatarLarge)
                            .background(
                                if (page == 0) {
                                    SolidColor(Color.White)
                                } else {
                                    Brush.linearGradient(TypeRushGradients.PrimaryGradient)
                                }
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Box(
                            modifier = modifier
                                .fillMaxSize()
                                .background(TypeRushColors.SurfaceGlass)
                        )

                        if(page == 0) {
                            Image(
                                painter = painterResource(id = R.drawable.icon),
                                contentDescription = "TypeRush Logo",
                                modifier = modifier.size(70.dp)
                            )
                        } else {
                            Icon(
                                imageVector = currentPageData.icon,
                                contentDescription = null,
                                modifier = modifier.size(60.dp),
                                tint = TypeRushColors.TextPrimary
                            )
                        }
                    }

                    Spacer(modifier = modifier.height(40.dp))

                    AnimatedContent(
                        targetState = currentPageData.title,
                        transitionSpec = {
                            slideInHorizontally(
                                animationSpec = spring(
                                    dampingRatio = Spring.DampingRatioMediumBouncy,
                                    stiffness = Spring.StiffnessLow
                                ),
                                initialOffsetX = { it / 2 }
                            ) togetherWith slideOutHorizontally(
                                animationSpec = tween(300),
                                targetOffsetX = { -it / 2 }
                            )
                        },
                        label = "titleAnimation"
                    ) { title ->
                        Text(
                            text = title,
                            style = TypeRushTextStyles.gameTitle.copy(
                                fontSize = 30.sp,
                                fontWeight = FontWeight.ExtraBold
                            ),
                            textAlign = TextAlign.Center,
                            color = TypeRushColors.TextPrimary
                        )
                    }

                    Spacer(modifier = modifier.height(20.dp))

                    AnimatedContent(
                        targetState = currentPageData.description,
                        transitionSpec = {
                            fadeIn(
                                animationSpec = spring(
                                    dampingRatio = Spring.DampingRatioMediumBouncy,
                                    stiffness = Spring.StiffnessLow
                                )
                            ) togetherWith fadeOut(animationSpec = tween(200))
                        },
                        label = "descriptionAnimation"
                    ) { description ->
                        Card(
                            modifier = modifier.padding(horizontal = 8.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = TypeRushColors.SurfaceGlass
                            ),
                            elevation = CardDefaults.cardElevation(0.dp),
                            shape = RoundedCornerShape(20.dp)
                        ) {
                            Text(
                                text = description,
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    color = TypeRushColors.TextSecondary,
                                    textAlign = TextAlign.Center,
                                    lineHeight = 28.sp,
                                    fontSize = 16.sp
                                ),
                                modifier = modifier.padding(
                                    horizontal = 24.dp,
                                    vertical = 20.dp
                                )
                            )
                        }
                    }
                }

                Spacer(modifier = modifier.weight(if (isLandscape) 0.5f else 1f))

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (page == pagerState.pageCount - 1) {
                        SignInButton(
                            isLoading = state.isLoading,
                            onClick = { viewModel.onIntent(AuthIntent.SignInWithGoogle) },
                            modifier = modifier
                        )
                    } else {
                        val infiniteTransition = rememberInfiniteTransition(label = "continueText")
                        val alpha by infiniteTransition.animateFloat(
                            initialValue = 0.6f,
                            targetValue = 1f,
                            animationSpec = infiniteRepeatable(
                                animation = tween(1500, easing = FastOutSlowInEasing),
                                repeatMode = RepeatMode.Reverse
                            ),
                            label = "continueAlpha"
                        )

                        Text(
                            text = ">> Slide to Continue",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = TypeRushColors.TextSecondary.copy(alpha = alpha),
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Medium
                            ),
                            textAlign = TextAlign.Center
                        )
                    }

                    Spacer(modifier = modifier.height(if (isLandscape) 16.dp else 24.dp))
                }
            }
        }
        TyperSnackBar(
            message = state.error ?: "",
            type = SnackBarType.ERROR,
            onDismiss = { viewModel.onIntent(AuthIntent.DismissError) },
            modifier = Modifier.align(Alignment.BottomCenter),
            duration = SnackbarDuration.Indefinite
        )
    }
}