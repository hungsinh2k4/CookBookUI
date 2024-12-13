package com.example.androidcookbook.ui.features.notification

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Comment
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.PersonAddAlt1
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androidcookbook.data.mocks.SampleNotifications
import com.example.androidcookbook.domain.model.notification.Notification
import com.example.androidcookbook.domain.model.notification.NotificationType
import com.example.androidcookbook.ui.common.text.TextWithBoldStrings
import com.example.androidcookbook.ui.components.post.SmallAvatar
import com.example.androidcookbook.ui.theme.AndroidCookbookTheme

val NotificationIconMap = mapOf(
    NotificationType.NEW_FOLLOWER to Icons.Default.PersonAddAlt1,
    NotificationType.NEW_POST_LIKE to Icons.Default.Favorite,
    NotificationType.NEW_COMMENT_LIKE to Icons.Default.Favorite,
    NotificationType.NEW_POST_COMMENT to Icons.AutoMirrored.Filled.Comment,
)

//val LightBlue = Color(0xFF03A9F4)
val LightBlue = Color(0xFF25A2F1)
val LightRed = Color(0xFFF44336)

val NotificationColorMap = mapOf(
    NotificationType.NEW_FOLLOWER to LightBlue,
    NotificationType.NEW_POST_COMMENT to LightBlue,
    NotificationType.NEW_POST_LIKE to LightRed,
    NotificationType.NEW_COMMENT_LIKE to LightRed,
)


@Composable
fun NotificationItem(
    notification: Notification,
    onClick: (Notification) -> Unit,
    modifier: Modifier = Modifier
) {
    var offsetX by remember { mutableFloatStateOf(0f) }
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick(notification) }
            .draggable(
                orientation = Orientation.Horizontal,
                state = rememberDraggableState { delta ->
                    offsetX += delta
                }
            )
    ) {
        Row(
            modifier = Modifier
                .weight(1f)
                .padding(16.dp),
        ) {
            Icon(
                imageVector = NotificationIconMap[notification.type] ?: Icons.Default.Notifications,
                contentDescription = "Notification Icon",
                tint = NotificationColorMap[notification.type] ?: Color.Black,
            )

            Column(
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                Row(
                    Modifier.padding(bottom = 8.dp)
                ) {
                    SmallAvatar(notification.imageURL)
                }

                TextWithBoldStrings(
                    notification.message,
                    style = MaterialTheme.typography.bodyMedium
                )

                // TODO maybe
//                Row(
//                    modifier = Modifier.padding(top = 8.dp),
//                ) {
//                    when (notification.type) {
//                        NotificationType.NEW_FOLLOWER -> {}
//                        NotificationType.NEW_POST_LIKE -> {
////                    val post: Post = getPost(notification.relatedId)
//                            val post = SamplePosts.posts.find { it.id == notification.relatedId }!!
//                            NotificationSupportingText(
//                                text = "${post.title} ${post.mainImage ?: ""}",
//                                modifier = Modifier
//                            )
//                        }
//
//                        NotificationType.NEW_COMMENT_LIKE -> {
////                        val comment = getComment(notification.relatedId)
//                            val comment =
//                                SamplePosts.posts.find { it.id == notification.relatedId }!!
//                        }
//
//                        NotificationType.NEW_POST_COMMENT -> {}
//                    }
//                }
            }
        }
    }
}

@Composable
private fun NotificationSupportingText(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        maxLines = 4,
        overflow = TextOverflow.Ellipsis,
        fontSize = 14.sp,
        color = LocalContentColor.current.copy(alpha = 0.8f)
    )
}

@Composable
@Preview(showBackground = true)
private fun NotificationItemPreview() {
    AndroidCookbookTheme {
        NotificationItem(
            notification = SampleNotifications.notifications[1],
            onClick = {}
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun NotificationItemUnreadPreview() {
    AndroidCookbookTheme {
        NotificationItem(
            notification = SampleNotifications.notifications[2],
            onClick = {}
        )
    }
}