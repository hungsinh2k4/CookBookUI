package com.example.androidcookbook.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androidcookbook.R
import com.example.androidcookbook.ui.theme.AndroidCookbookTheme

@Composable
fun CookbookBottomNavigationBar(
    onHomeClick: () -> Unit,
    onChatClick: () -> Unit,
    onNewsfeedClick: () -> Unit,
    onUserProfileClick: () -> Unit,
) {
    NavigationBar(
        containerColor = Color.Transparent,
        modifier = Modifier
            .height(96.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            IconButton(
                onClick = onHomeClick,
                modifier = Modifier.weight(1F)
            ) {
                Icon(
                    painter = painterResource(R.drawable.icon_category),
                    contentDescription = "Home"
                )
            }
            IconButton(
                onClick = onChatClick,
                modifier = Modifier.weight(1F)
            ) {
                Icon(
                    painter = painterResource(R.drawable.icon_chat),
                    contentDescription = "AI Chat"
                )
            }
            IconButton(
                onClick = onNewsfeedClick,
                modifier = Modifier.weight(1F)
            ) {
                Icon(
                    painter = painterResource(R.drawable.icon_newsfeed),
                    contentDescription = "News Feed"
                )
            }
            IconButton(
                onClick = onUserProfileClick,
                modifier = Modifier.weight(1F)
            ) {
                Icon(
                    painter = painterResource(R.drawable.icon_user_profile),
                    contentDescription = "User profile"
                )
            }
        }
    }
}

@Preview
@Composable
fun NavBarPreview() {
    AndroidCookbookTheme {
        CookbookBottomNavigationBar({},{},{},{},)
    }
}

@Preview
@Composable
fun NavBarDarkPreview() {
    AndroidCookbookTheme(darkTheme = true) {
        CookbookBottomNavigationBar({},{},{},{},)
    }
}