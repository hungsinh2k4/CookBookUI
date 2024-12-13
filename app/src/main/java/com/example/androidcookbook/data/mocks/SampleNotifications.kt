package com.example.androidcookbook.data.mocks

import com.example.androidcookbook.domain.model.notification.Notification
import com.example.androidcookbook.domain.model.notification.NotificationType

object SampleNotifications {
    val notifications = buildList {
        repeat(10) {
            add(
                when (it % 4) {
                    0 -> Notification(
                        id = it,
                        type = NotificationType.NEW_FOLLOWER,
                        message = "**Ly Duc** followed you",
                        relatedId = 1,
                        isRead = false,
                        createdAt = "2024-01-28T00:00:00.000Z",
                        imageURL = null,
                    )
                    1 -> Notification(
                        id = it,
                        type = NotificationType.NEW_POST_LIKE,
                        message = "**Ly Duc** liked your post",
                        relatedId = 1,
                        isRead = true,
                        createdAt = "2024-01-28T00:00:00.000Z",
                        imageURL = null,
                    )
                    2 -> Notification(
                        id = it,
                        type = NotificationType.NEW_POST_COMMENT,
                        message = "**Ly Duc** commented on your post",
                        relatedId = 1,
                        isRead = true,
                        createdAt = "2024-01-28T00:00:00.000Z",
                        imageURL = null,
                    )
                    else -> Notification(
                        id = it,
                        type = NotificationType.NEW_COMMENT_LIKE,
                        message = "**Ly Duc** liked your comment",
                        relatedId = 1,
                        isRead = false,
                        createdAt = "2024-01-28T00:00:00.000Z",
                        imageURL = null,
                    )
                }
            )
        }
    }
}