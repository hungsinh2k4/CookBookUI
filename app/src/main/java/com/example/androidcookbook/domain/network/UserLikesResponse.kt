package com.example.androidcookbook.domain.network

import com.example.androidcookbook.domain.model.post.Post

data class UserLikesResponse (
    val nextPage: Boolean,
    val likes: List<Post>,
)
