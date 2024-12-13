package com.example.androidcookbook.ui.features.notification

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androidcookbook.data.mocks.SampleNotifications
import com.example.androidcookbook.domain.model.notification.Notification
import com.example.androidcookbook.ui.components.EndlessLazyColumn
import com.example.androidcookbook.ui.theme.AndroidCookbookTheme

@Composable
fun NotificationScreen(
    notifications: List<Notification>,
    onNotificationClick: (Notification) -> Unit,
    loadMore: () -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    isClearing: Boolean, // New state variable
) {
    val itemsToShow = remember(notifications, isClearing) {
        if (isClearing) emptyList() else notifications
    }
    EndlessLazyColumn(
        items = notifications,
        itemKey = { it.id },
        loadMore = loadMore,
        modifier = modifier,
        contentPadding = contentPadding
    ) { index, notification ->
        val itemDelay = (index * 30).coerceAtMost(180) // Calculate delay (50ms per item, max 500ms)
        AnimatedVisibility(
            visible = !isClearing,
            exit = slideOutHorizontally(
                animationSpec = tween(durationMillis = 300, delayMillis = itemDelay),
            ) { it } ,
        ) {
            NotificationItem(
                notification = notification,
                onClick = onNotificationClick,
                if (notification.isRead) Modifier
                else Modifier.background(LightBlue.copy(alpha = 0.1f)),
            )

        }
        HorizontalDivider()
    }
    if (notifications.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("No notifications yet")
        }
    }
}

@Composable
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
private fun NotificationScreenDarkPreview() {
    AndroidCookbookTheme {
        NotificationScreen(
            notifications = SampleNotifications.notifications,
            onNotificationClick = {},
            loadMore = {},
            isClearing = false,
        )
    }
}

@Composable
@Preview
private fun NotificationScreenPreview() {
    AndroidCookbookTheme {
        NotificationScreen(
            notifications = SampleNotifications.notifications,
            onNotificationClick = {},
            loadMore = {},
            isClearing = false,
        )
    }
}