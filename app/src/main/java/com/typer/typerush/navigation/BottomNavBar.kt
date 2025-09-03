package com.typer.typerush.navigation

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.typer.typerush.ui.theme.TypeRushColors
import kotlin.collections.forEachIndexed

@Composable
fun BottomNavBar(
    modifier: Modifier = Modifier,
    selectedRoute: BottomBarScreen,
    onTabSelected: (BottomBarScreen) -> Unit = {},
) {
    NavigationBar(
        containerColor = Color.Transparent,
        contentColor = TypeRushColors.TextPrimary,
        modifier = Modifier.height(80.dp)
    ) {
        val items = listOf(
//            BottomBarScreen.Home,
            BottomBarScreen.Practice,
            BottomBarScreen.Compete,
//            BottomBarScreen.Progress,
//            BottomBarScreen.Profile
        )
        Row(
            modifier = modifier
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            items.forEachIndexed { index, item ->
                NavigationBarItem(
                    selected = selectedRoute == item,
                    onClick = { onTabSelected(item) },
                    icon = {
                        AnimatedNavigationIcon(
                            item = item,
                            isSelected = selectedRoute == item
                        )
                    },
                    label = {
                        Text(
                            text = item.title,
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontWeight = if (selectedRoute == item) FontWeight.SemiBold else FontWeight.Medium,
                                fontSize = 11.sp
                            )
                        )
                    },
                    alwaysShowLabel = true,
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = TypeRushColors.Primary,
                        unselectedIconColor = TypeRushColors.TextSecondary.copy(alpha = 0.7f),
                        selectedTextColor = TypeRushColors.Primary,
                        unselectedTextColor = TypeRushColors.TextSecondary.copy(alpha = 0.7f),
                        indicatorColor = TypeRushColors.Primary.copy(alpha = 0.15f)
                    )
                )
            }
        }
    }
}

@Composable
fun AnimatedNavigationIcon(
    item: BottomBarScreen,
    isSelected: Boolean,
    modifier: Modifier = Modifier
) {
    val scale by animateFloatAsState(
        targetValue = if (isSelected) 1.2f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "iconScale"
    )

    BadgedBox(
        badge = {
            if (item.hasNews) {
                Badge(
                    containerColor = TypeRushColors.Secondary,
                    contentColor = TypeRushColors.TextPrimary,
                    modifier = Modifier.scale(0.8f)
                ) {
                    Text(
                        text = "•",
                        fontSize = 8.sp
                    )
                }
            }
        }
    ) {
        Icon(
            imageVector = if (isSelected) item.selectedIcon else item.unselectedIcon,
            contentDescription = item.title,
            modifier = modifier.scale(scale)
        )
    }
}