package com.example.androidcookbook.domain.model.recipe

data class RecipeList(
    val meals: List<Recipe>
)

data class Recipe(
    val idMeal: Int,
    val strMeal: String,
    val strCategory: String,
    val strArea: String,
    val strInstructions: String,
    val strMealThumb: String,
)

