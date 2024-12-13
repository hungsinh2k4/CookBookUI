package com.example.androidcookbook.domain.model.notification

enum class NotificationType {
    NEW_FOLLOWER,
    NEW_POST_LIKE,
    NEW_POST_COMMENT,
    NEW_COMMENT_LIKE,
}

data class Notification(
    val id: Int,
    val type: NotificationType,
    val message: String,
    val relatedId: Int,
    val isRead: Boolean,
    val createdAt: String,
    val imageURL: String?,
)
