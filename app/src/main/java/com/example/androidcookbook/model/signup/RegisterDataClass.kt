package com.example.androidcookbook.model.signup

data class RegisterRequest(
    val username: String,
    val password: String,
    val email: String
)

data class RegisterResponse(
    val statusCode: Int,
    val timestamp: String,
    val path: String,
    val message: Any,
    val error: String
)
