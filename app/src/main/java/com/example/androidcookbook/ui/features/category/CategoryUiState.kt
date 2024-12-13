package com.example.androidcookbook.ui.features.category

import com.example.androidcookbook.domain.model.category.Category
import com.example.androidcookbook.domain.model.recipe.Recipe
import com.example.androidcookbook.domain.model.recipe.RecipeDetails

data class CategoryUiState (
//    data object Success : CategoryUiState
//    data class RecipeDetail(val recipeDetails: RecipeDetails): CategoryUiState
//    data object ListDetail: CategoryUiState
//    data object Error : CategoryUiState
//    data object Loading : CategoryUiState

    val isCategory: Boolean = true,
    val isRecipeDetail: Boolean = false,
    val isListDetail: Boolean = false,
    val isError: Boolean = false,
    val isLoading: Boolean = false,
    val isLoadingRecipeList: Boolean = false,
    val isLoadingRecipeDetails: Boolean = false,

    )
