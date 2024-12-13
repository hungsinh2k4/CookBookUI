package com.example.androidcookbook.domain.model.auth

data class ResetPasswordRequest(
    val email: String,
    val token: String,
    val password: String,
)

data class OtpValidationRequest(
    val email: String,
    val code: String,
)

data class OtpValidationResponse(
    val token: String,
    val message: String
)
