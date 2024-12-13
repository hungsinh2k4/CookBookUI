package com.example.androidcookbook.domain.model.aigen

import com.example.androidcookbook.domain.model.ingredient.Ingredient

data class AiResult (
    val recipes: List<cookingInstruction>
)

data class cookingInstruction (
    val ingredients: List<Ingredient>,
    val name: String,
    val steps: List<String>
)
