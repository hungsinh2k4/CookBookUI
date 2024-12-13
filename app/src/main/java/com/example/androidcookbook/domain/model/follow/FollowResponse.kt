package com.example.androidcookbook.domain.model.follow

import com.example.androidcookbook.domain.model.user.User

data class FollowersResponse(
    val followers: List<User>,
)

data class FollowingResponse(
    val following: List<User>,
)