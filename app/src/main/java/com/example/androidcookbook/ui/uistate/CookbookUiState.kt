package com.example.androidcookbook.ui.uistate

data class CookbookUiState (
    val canNavigateBack: Boolean = false,
    val searchQuery: String = "",
)