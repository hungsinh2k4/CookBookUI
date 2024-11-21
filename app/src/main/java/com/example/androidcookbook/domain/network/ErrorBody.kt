package com.example.androidcookbook.domain.network

import kotlinx.serialization.Serializable

@Serializable
data class ErrorBody(
    val statusCode: Int,
    val timestamp: String,
    val path: String,
    @Serializable (with = MessageSerializer::class)
    val message: List<String>,
    val error: String,
)

