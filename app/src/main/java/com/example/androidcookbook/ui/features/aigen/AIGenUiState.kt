package com.example.androidcookbook.ui.features.aigen

import com.example.androidcookbook.domain.model.ingredient.Ingredient
import com.google.gson.annotations.Expose


data class AIGenUiState(
    @Expose val ingredients: MutableList<Ingredient> = mutableListOf(Ingredient("","")),
    val recipes: MutableList<String> = mutableListOf(""),
    val note: String = "",
    val isTakingInput: Boolean = true,
    val isProcessing: Boolean = false,
    val isDoneUploadingImage:Boolean = false,
    val isDone: Boolean = false,
)