package com.example.androidcookbook.ui.uistate

data class SignUiState(
    val isSignIn: Boolean = true,
    val openDialog: Boolean = false,
    val dialogMessage: String = "",
    val signInSuccess: Boolean = false,
    val signedIn: Boolean = false
)
