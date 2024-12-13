package com.example.androidcookbook.ui.common.appbars

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import com.example.androidcookbook.R
import com.example.androidcookbook.domain.model.user.User
import com.example.androidcookbook.ui.nav.Routes
import com.example.androidcookbook.ui.nav.utils.hasRoute

@Composable
fun CookbookBottomNavigationBar(
    onCategoryClick: () -> Unit,
    onAiChatClick: () -> Unit,
    onNewsfeedClick: () -> Unit,
    onUserProfileClick: () -> Unit,
    onCreatePostClick: () -> Unit,
    currentUser: User,
    currentDestination: NavDestination? = null,
) {

        val colors = NavigationBarItemDefaults.colors(

            indicatorColor = MaterialTheme.colorScheme.secondary,
            selectedIconColor = MaterialTheme.colorScheme.primary,
            selectedTextColor = MaterialTheme.colorScheme.onSecondary,

        )

        Column {
            HorizontalDivider(
                color = DividerDefaults.color.copy(alpha = 0.5F)
            )

            NavigationBar(
                containerColor = Color.Transparent,
                modifier = Modifier
                    .padding(WindowInsets.navigationBars.asPaddingValues())
                    .consumeWindowInsets(WindowInsets.navigationBars.asPaddingValues())
                    .padding(bottom = 4.dp)
                    .heightIn(max = 50.dp),
            ) {
                NewsfeedNavigationBarItem(currentDestination, onNewsfeedClick, colors)

                AiChatNavigationBarItem(currentDestination, onAiChatClick, colors)

                NavigationBarItem(
                    selected = currentDestination?.hasRoute(Routes.CreatePost) == true,
                    onClick = onCreatePostClick,
                    icon = {
                        Icon(
//                            painter = painterResource(R.drawable.add_box_24dp_e8eaed_fill1_wght400_grad0_opsz24),
                            imageVector = Icons.Default.AddCircle,
                            contentDescription = "Create post",
                            tint = LocalContentColor.current.copy(alpha = 0.8F),
                            modifier = Modifier.scale(1.2F)
                        )
                    },
                    alwaysShowLabel = false,
                    colors = colors
                )

                CategoryNavigationBarItem(currentDestination, onCategoryClick, colors)

                UserProfileNavigationBarItem(currentDestination, currentUser, onUserProfileClick, colors)
            }
        }

}

@Composable
private fun RowScope.CookbookNavigationBarItem(
    currentDestination: NavDestination?,
    route: Any,
    onClick: () -> Unit,
    icon: @Composable () -> Unit,
    label: @Composable () -> Unit = {},
    alwaysShowLabel: Boolean,
    colors: NavigationBarItemColors,
) {
    NavigationBarItem(
        selected = currentDestination?.hasRoute(route) == true,
        onClick = onClick,
        icon = {
            Box(
                modifier = Modifier.size(24.dp)
            ) {
                icon()
            }
        },
//        label = label,
        alwaysShowLabel = alwaysShowLabel,
        colors = colors
    )
}

@Composable
private fun RowScope.UserProfileNavigationBarItem(
    currentDestination: NavDestination?,
    currentUser: User,
    onUserProfileClick: () -> Unit,
    colors: NavigationBarItemColors
) {
    CookbookNavigationBarItem(
        currentDestination = currentDestination,
        route = Routes.App.UserProfile(currentUser.id),
        onClick = onUserProfileClick,
        icon = {
            Icon(
                painter = painterResource(R.drawable.icon_user_profile),
                contentDescription = "User Profile",
//                tint = Color(0xFF0D1114)
            )
        },
//        label = {
//            Text(
//                text = "Me",
//                maxLines = 1,
//            )
//        },
        alwaysShowLabel = true,
        colors = colors
    )
}

@Composable
private fun RowScope.NewsfeedNavigationBarItem(
    currentDestination: NavDestination?,
    onNewsfeedClick: () -> Unit,
    colors: NavigationBarItemColors
) {
    CookbookNavigationBarItem(
        currentDestination,
        route = Routes.App.Newsfeed,
        onClick = onNewsfeedClick,
        icon = {
            Icon(
                painter = painterResource(R.drawable.home),
                contentDescription = "Newsfeed",
                modifier = Modifier,
//                tint = Color(0xFF0D1114)

            )
        },
//        label = {
//            Text(
//                text = Routes.App.Newsfeed::class.java.simpleName,
//                maxLines = 1
//            )
//        },
        alwaysShowLabel = true,
        colors = colors
    )
}

@Composable
private fun RowScope.AiChatNavigationBarItem(
    currentDestination: NavDestination?,
    onChatClick: () -> Unit,
    colors: NavigationBarItemColors
) {
    CookbookNavigationBarItem(
        currentDestination = currentDestination,
        route = Routes.App.AIChef,
        onClick = onChatClick,
        icon = {
            Icon(
                painter = painterResource(R.drawable.ai_gen_light_mode),
                contentDescription = "Chat",
                modifier = Modifier.scale(1.2F),
//                tint = Color(0xFF0D1114)

            )
        },
//        label = {
//            Text(
//                text = Routes.App.AIChat::class.java.simpleName,
//                maxLines = 1
//            )
//        },
        alwaysShowLabel = true,
        colors = colors
    )
}

@Composable
private fun RowScope.CategoryNavigationBarItem(
    currentDestination: NavDestination?,
    onCategoryClick: () -> Unit,
    colors: NavigationBarItemColors,
) {
    CookbookNavigationBarItem(
        currentDestination = currentDestination,
        route = Routes.App.Category,
        onClick = onCategoryClick,
        icon = {
            Icon(
                painter = painterResource(R.drawable.icon_category),
                contentDescription = "Home",
//                tint = Color(0xFF0D1114)

            )
        },
//        label = {
//            Text(
//                text = Routes.App.Category::class.java.simpleName,
//                maxLines = 1
//            )
//        },
        alwaysShowLabel = true,
        colors = colors
    )
}

@Preview(showBackground = true)
@Composable
fun NavBarPreview() {
    AppBarTheme {
        CookbookBottomNavigationBar({}, {}, {}, {}, {}, currentUser = User())
    }
}

@Preview
@Composable
fun NavBarDarkPreview() {
    AppBarTheme(darkTheme = true) {
        CookbookBottomNavigationBar(
            {}, {}, {}, {}, {}, currentUser = User()
        )
    }
}

@Preview
@Composable
fun NavBarItemPreview() {
    AppBarTheme(darkTheme = true) {
        Row {
            NavigationBarItem(
                selected = true,
                onClick = { },
                icon = {
                    Box(
                        modifier = Modifier.size(24.dp)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.home),
                            contentDescription = "Newsfeed",
                            modifier = Modifier,
                        )
                    }
                },
//        label = label,
                alwaysShowLabel = false,
            )
        }
    }
}