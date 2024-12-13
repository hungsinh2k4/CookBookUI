package com.example.androidcookbook.domain.network

import com.example.androidcookbook.domain.model.post.Post

data class UserFavoritesResponse(
    val nextPage: Boolean,
    val favorites: List<Post>,
)
