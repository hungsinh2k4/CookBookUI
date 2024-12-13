package com.example.androidcookbook.domain.model.post

import com.example.androidcookbook.domain.model.user.User
import kotlinx.serialization.Serializable

const val CREATED_AT_DEFAULT = "2024-01-01T00:00:00.000Z"

@Serializable
data class Comment(
    val id: Int = 0,
    val user: User = User(),
    val content: String = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
    val createdAt: String = CREATED_AT_DEFAULT,
    val totalLike: Int = 0,
    val isLiked: Boolean = false,
)
