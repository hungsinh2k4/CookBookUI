package com.example.androidcookbook.ui.features.category

import com.example.androidcookbook.domain.model.category.Category

sealed interface CategoryUiState{
    data class Success(val categories: List<Category>) : CategoryUiState
    data object Error : CategoryUiState
    data object Loading : CategoryUiState
}
