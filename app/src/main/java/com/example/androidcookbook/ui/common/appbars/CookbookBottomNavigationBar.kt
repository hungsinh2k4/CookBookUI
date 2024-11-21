package com.example.androidcookbook.ui.common.appbars

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination
import com.example.androidcookbook.R
import com.example.androidcookbook.ui.nav.Routes
import com.example.androidcookbook.ui.nav.utils.hasRoute
import com.example.androidcookbook.ui.theme.AndroidCookbookTheme

@Composable
fun CookbookBottomNavigationBar(
    onHomeClick: () -> Unit,
    onChatClick: () -> Unit,
    onNewsfeedClick: () -> Unit,
    onUserProfileClick: () -> Unit,
    currentDestination: NavDestination? = null,
) {
    NavigationBar (
        containerColor = Color.Transparent,
        modifier = Modifier
    ) {
        NavigationBarItem(
            selected = currentDestination?.hasRoute(Routes.App.Category) == true,
            onClick = onHomeClick,
            icon = {
                Icon(
                    painter = painterResource(R.drawable.icon_category),
                    contentDescription = "Home"
                )
            },
            label = {
                Text(text = Routes.App.Category::class.java.simpleName)
            },
            alwaysShowLabel = false,
        )
        NavigationBarItem(
            selected = currentDestination?.hasRoute(Routes.App.AIChat) == true,
            onClick = onChatClick,
            icon = {
                Icon(
                    painter = painterResource(R.drawable.icon_chat),
                    contentDescription = "Chat"
                )
            },
            label = {
                Text(text = Routes.App.AIChat::class.java.simpleName)
            },
            alwaysShowLabel = false,
        )
        NavigationBarItem(
            selected = currentDestination?.hasRoute(Routes.App.Newsfeed) == true,
            onClick = onNewsfeedClick,
            icon = {
                Icon(
                    painter = painterResource(R.drawable.icon_newsfeed),
                    contentDescription = "Newsfeed"
                )
            },
            label = {
                Text(text = Routes.App.Newsfeed::class.java.simpleName)
            },
            alwaysShowLabel = false,
        )
        NavigationBarItem(
            selected = currentDestination?.hasRoute(Routes.App.UserProfile(0)) == true,
            onClick = onUserProfileClick,
            icon = {
                Icon(
                    painter = painterResource(R.drawable.icon_user_profile),
                    contentDescription = "User Profile"
                )
            },
            label = {
                Text(text = Routes.App.UserProfile::class.java.simpleName)
            },
            alwaysShowLabel = false,
        )
    }
}

@Preview
@Composable
fun NavBarPreview() {
    AndroidCookbookTheme {
        CookbookBottomNavigationBar({}, {}, {}, {},)
    }
}

@Preview
@Composable
fun NavBarDarkPreview() {
    AndroidCookbookTheme(darkTheme = true) {
        CookbookBottomNavigationBar({}, {}, {}, {},)
    }
}