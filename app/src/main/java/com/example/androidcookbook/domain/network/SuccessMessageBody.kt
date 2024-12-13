package com.example.androidcookbook.domain.network

data class SuccessMessageBody(
    val statusCode: Int,
    val timestamp: String,
    val path: String,
    val message: String,
    val error: String
)