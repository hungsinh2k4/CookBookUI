package com.example.androidcookbook.domain.model.post

import com.example.androidcookbook.domain.model.ingredient.Ingredient

data class PostCreateRequest(
    val title: String,
    val description: String,
    val mainImage: String?,
    val cookTime: Int?,
    val ingredient: List<Ingredient>?,
    val steps: List<String>?,
)

data class PostCreateResponse(
    val message: String,
    val post: Post,
)
