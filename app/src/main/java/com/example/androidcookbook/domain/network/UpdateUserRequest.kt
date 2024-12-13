package com.example.androidcookbook.domain.network

data class UpdateUserRequest (
    val avatar: String?,
    val name: String,
    val banner: String?,
    val bio: String?,
)
