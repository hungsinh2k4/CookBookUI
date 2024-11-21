package com.example.androidcookbook.ui.uistate

import com.example.androidcookbook.model.Category

sealed interface CategoryUiState{
    data class Success(val categories: List<Category>) : CategoryUiState
    data object Error : CategoryUiState
    data object Loading : CategoryUiState
}
