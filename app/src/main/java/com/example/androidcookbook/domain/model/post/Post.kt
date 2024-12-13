package com.example.androidcookbook.domain.model.post

import com.example.androidcookbook.domain.model.ingredient.Ingredient
import com.example.androidcookbook.domain.model.user.User
import kotlinx.serialization.Serializable

@Serializable
data class Post(
    val id: Int = 0,
    val author: User = User(),
    val title: String = "",
    val description: String = "",
    val cookTime: String? = null,
    val mainImage: String? = null,
    val totalView: Int = 0,
    val totalComment: Int = 0,
    val totalLike: Int = 0,
    val ingredient: List<Ingredient>? = null,
    val steps: List<String>? = null,
    val createdAt: String = "",
)