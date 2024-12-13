package com.example.androidcookbook.domain.model.post

import com.example.androidcookbook.domain.model.user.User

data class PostLikesResponse(
    val nextPage: Boolean,
    val likes: List<User>
)