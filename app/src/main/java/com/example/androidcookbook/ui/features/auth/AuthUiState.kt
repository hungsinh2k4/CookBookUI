package com.example.androidcookbook.ui.features.auth

data class AuthUiState(
    val openDialog: Boolean = false,
    val dialogMessage: String = "",
    val signInSuccess: Boolean = false,
)
