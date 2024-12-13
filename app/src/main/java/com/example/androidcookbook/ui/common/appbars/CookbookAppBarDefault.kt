package com.example.androidcookbook.ui.common.appbars

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androidcookbook.domain.model.user.GUEST_ID
import com.example.androidcookbook.domain.model.user.User

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CookbookAppBarDefault(
    modifier: Modifier = Modifier,
    showBackButton: Boolean = false,
    notificationCount: Int,
    onNotificationClick: () -> Unit = {},
    onSearchButtonClick: () -> Unit = {},
    onSettingsClick: () -> Unit = {},
    onBackButtonClick: () -> Unit = {},
    scrollBehavior: TopAppBarScrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(),
    onLogoutClick: () -> Unit = {},
    currentUser: User = User()
) {
    var menuExpanded by remember { mutableStateOf(false) } // State to control menu visibility

    TopAppBar(
        modifier = modifier,
        colors = defaultTopAppBarColor(),
        title = {
            Text(
                text = "Cookbook",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
        },
        actions = {
            Box(modifier = Modifier.padding(end = 6.dp)) {
                IconButton(
                    onClick = onSearchButtonClick,
                    modifier = Modifier.size(36.dp),
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search",
                        tint = MaterialTheme.colorScheme.secondary
                    )
                }
            }

            BadgedBox (
                badge = {
                    if (notificationCount > 0) {
                        Badge(
                            containerColor = Color.Red,
                            contentColor = Color.White,
                        ) {
                            Text(text = notificationCount.toString())
                        }
                    }
                },
                modifier = Modifier.padding(end = 6.dp)
            ) {
                IconButton(
                    onClick = onNotificationClick,
                    modifier = Modifier.size(30.dp),
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = Color.Transparent
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Notifications,
                        contentDescription = "Notifications",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            }

            Box(modifier = Modifier.padding(end = 6.dp)) {
                IconButton(
                    onClick = {menuExpanded = !menuExpanded},
                    modifier = Modifier.size(30.dp),
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = Color.Transparent,
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = "Menu Button",
                        tint = MaterialTheme.colorScheme.secondary
                    )
                    DropdownMenu(
                        expanded = menuExpanded,
                        onDismissRequest = { menuExpanded = false }, // Close the menu when clicked outside
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.surfaceContainer) // Set background to white
                            .clip(RoundedCornerShape(12.dp)) // Consistent corner rounding
                    ) {
                        DropdownMenuItem(
                            onClick = {
                                menuExpanded = false
                                onSettingsClick()
                            },
                            text = {
                                Text(
                                    text = "Settings",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurface // Black text for contrast
                                )
                            },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Settings,
                                    contentDescription = null,
                                )
                            },
                            modifier = Modifier
                                .padding(horizontal = 0.dp) // No extra padding to align with the main menu
                                .height(48.dp)
                                .fillMaxWidth()
                        )
                        DropdownMenuItem(
                            onClick = {
                                menuExpanded = false
                                onLogoutClick()
                            },
                            text = {
                                Text(
                                    text = if (currentUser.id == GUEST_ID) "Login" else "Logout",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurface // Black text for contrast
                                )
                            },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                                    contentDescription = null,

                                )
                            },
                            modifier = Modifier
                                .padding(horizontal = 0.dp) // No extra padding to align with the main menu
                                .height(48.dp)
                                .fillMaxWidth()
                        )
                    }
                }
            }
        },
        navigationIcon = {
            if (showBackButton) {
                IconButton(onClick = onBackButtonClick) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.ArrowBack,
                        contentDescription = "Back Button",
                        tint = MaterialTheme.colorScheme.secondary
                    )
                }
            }
        },
        scrollBehavior = scrollBehavior
    )
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun defaultTopAppBarColor() = TopAppBarDefaults.topAppBarColors(
    containerColor = Color.Transparent,
    scrolledContainerColor = Color.Transparent,
    navigationIconContentColor = MaterialTheme.colorScheme.secondary,
    actionIconContentColor = MaterialTheme.colorScheme.secondary,
    titleContentColor = MaterialTheme.colorScheme.onSurface,
)

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun TopAppBarPreview() {
    AppBarTheme {
        CookbookAppBarDefault(
            notificationCount = 0
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun TopAppBarDarkPreview() {
    AppBarTheme(darkTheme = true) {
        CookbookAppBarDefault(
            notificationCount = 1
        )
    }
}