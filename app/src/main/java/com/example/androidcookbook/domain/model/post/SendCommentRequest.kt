package com.example.androidcookbook.domain.model.post

import kotlinx.serialization.Serializable

data class SendCommentRequest(
    val content: String
)

@Serializable
data class GetCommentResponse(
    val nextPage: Boolean,
    val comments: List<Comment>
)
