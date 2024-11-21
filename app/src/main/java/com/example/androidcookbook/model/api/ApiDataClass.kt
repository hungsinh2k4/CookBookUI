package com.example.androidcookbook.model.api

data class ApiResponse(
    val statusCode: Int,
    val timestamp: String,
    val path: String,
    val message: Any,
    val error: String
)
