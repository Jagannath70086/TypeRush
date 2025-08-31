package com.typer.typerush.landing_home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.typer.typerush.compete.presentation.CompeteScreen
import com.typer.typerush.core.components.SnackBarType
import com.typer.typerush.core.components.TyperSnackBar
import com.typer.typerush.core.session.SessionManager
import com.typer.typerush.landing_home.components.HomeAppBar
import com.typer.typerush.navigation.BottomBarScreen
import com.typer.typerush.navigation.BottomNavBar
import com.typer.typerush.practice.presentation.PracticeScreen
import com.typer.typerush.ui.theme.TypeRushColors
import com.typer.typerush.ui.theme.TypeRushTextStyles
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun LandingHomeScreen(
    sessionManager: SessionManager = koinInject(),
    viewModel: LandingHomeViewModel = koinViewModel()
) {
    val user by sessionManager.currentUser.collectAsState()
    val state by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            HomeAppBar(
                user = user,
                onLogout = { viewModel.onIntent(LandingHomeIntent.ShowLogoutDialog) }
            )
        },
        bottomBar = {
            BottomNavBar(
                selectedRoute = state.selectedRoute,
                onTabSelected = { viewModel.onIntent(LandingHomeIntent.NavigateToBottomBarScreen(it)) }
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
        ) {
            when (state.selectedRoute) {
                BottomBarScreen.Home -> null
                BottomBarScreen.Practice -> PracticeScreen()
                BottomBarScreen.Compete -> CompeteScreen()
                BottomBarScreen.Progress -> null
                BottomBarScreen.Profile -> null
            }
            TyperSnackBar(
                message = state.error ?: "",
                type = SnackBarType.ERROR,
                onDismiss = { viewModel.onIntent(LandingHomeIntent.DismissError) },
                duration = SnackbarDuration.Indefinite,
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }
    }

    if (state.showLogoutDialog) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            Color.Black.copy(alpha = 0.6f),
                            Color.Black.copy(alpha = 0.8f)
                        ),
                        radius = 1000f
                    )
                )
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() },
                    enabled = !state.isLoading
                ) {
                    if (!state.isLoading) {
                        viewModel.onIntent(LandingHomeIntent.DismissLogoutDialog)
                    }
                }
        )
        AlertDialog(
            onDismissRequest = {
                if (!state.isLoading) {
                    viewModel.onIntent(LandingHomeIntent.DismissLogoutDialog)
                }
            },
            title = {
                Text(
                    text = "Logout",
                    style = TypeRushTextStyles.gameTitle.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp
                    ),
                    color = TypeRushColors.TextPrimary
                )
            },
            text = {
                Text(
                    text = "Are you sure you want to logout? You'll need to sign in again to access your account.",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        lineHeight = 20.sp
                    ),
                    color = TypeRushColors.TextSecondary
                )
            },
            confirmButton = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    TextButton(
                        onClick = {
                            if (!state.isLoading) {
                                viewModel.onIntent(LandingHomeIntent.DismissLogoutDialog)
                            }
                        },
                        colors = ButtonDefaults.textButtonColors(
                            contentColor = if (state.isLoading)
                                TypeRushColors.TextSecondary.copy(alpha = 0.4f)
                            else
                                TypeRushColors.TextSecondary
                        ),
                        modifier = Modifier
                            .height(36.dp)
                            .border(
                                width = 1.dp,
                                color = if (state.isLoading)
                                    Color.White.copy(alpha = 0.3f)
                                else
                                    Color.White.copy(alpha = 0.8f),
                                shape = RoundedCornerShape(16.dp)
                            )
                            .height(24.dp),
                        enabled = !state.isLoading
                    ) {
                        Text(
                            text = "Cancel",
                            style = MaterialTheme.typography.labelLarge.copy(
                                fontWeight = FontWeight.Medium
                            ),
                            color = if (state.isLoading)
                                TypeRushColors.TextSecondary.copy(alpha = 0.4f)
                            else
                                TypeRushColors.TextSecondary
                        )
                    }
                    ElevatedButton(
                        onClick = {
                            viewModel.onIntent(LandingHomeIntent.Logout)
                        },
                        colors = ButtonDefaults.elevatedButtonColors(
                            containerColor = Color(0xFFE57373),
                            contentColor = Color.White
                        ),
                        elevation = ButtonDefaults.elevatedButtonElevation(
                            defaultElevation = 4.dp,
                            pressedElevation = 8.dp
                        ),
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier.padding(start = 8.dp),
                        enabled = !state.isLoading
                    ) {
                        if (state.isLoading) {
                            CircularProgressIndicator(
                                color = Color.White,
                                strokeWidth = 2.dp,
                                modifier = Modifier.size(16.dp)
                            )
                        } else {
                            Text(
                                text = "Logout",
                                style = MaterialTheme.typography.labelLarge.copy(
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }
                    }
                }
            },
            containerColor = MaterialTheme.colorScheme.surface,
            titleContentColor = TypeRushColors.TextPrimary,
            textContentColor = TypeRushColors.TextSecondary,
            shape = RoundedCornerShape(24.dp),
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .shadow(
                    elevation = 12.dp,
                    shape = RoundedCornerShape(24.dp),
                    ambientColor = Color.Black.copy(alpha = 0.1f),
                    spotColor = Color.Black.copy(alpha = 0.15f)
                )
                .border(
                    width = 1.dp,
                    color = Color.White.copy(alpha = 0.8f),
                    shape = RoundedCornerShape(24.dp)
                )
        )
    }
}