package com.example.androidcookbook.domain.model.aigen


import com.example.androidcookbook.domain.model.ingredient.Ingredient


data class AiGenFromImage(
    val ingredients: List<Ingredient>,
    val recipes: List<String>
)



