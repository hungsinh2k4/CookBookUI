package com.example.androidcookbook.domain.model.auth

import com.example.androidcookbook.domain.model.user.User
import com.google.gson.annotations.SerializedName

data class SignInRequest(
    val username: String,
    val password: String
)

data class SignInResponse(
    @SerializedName("access_token") val accessToken: String,
    val message: String,
    val user: User,
)
