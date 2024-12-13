package com.example.androidcookbook.data.mocks

import com.example.androidcookbook.domain.model.ingredient.Ingredient
import com.example.androidcookbook.domain.model.post.Post
import com.example.androidcookbook.domain.model.user.User

object SamplePosts {
    val posts: List<Post> = buildList {
        repeat(10) {
            add(
                Post(
                    id = it,
                    author = User(
                        id = 1,
                        name = "Ly Duc",
                        avatar = null,
                        bio = "I like suffering",
                        totalFollowers = 0,
                        totalFollowing = 0
                    ),
                    title = "Shrimp salad cooking :)",
                    description = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard.",
                    cookTime = "",
                    mainImage = "https://example.com/image${it}.jpg",
                    createdAt = "2024-01-28T00:00:00.000Z",
                    totalView = 0,
                    totalLike = 0,
                    totalComment = 0,
                    ingredient = listOf(
                        Ingredient("Ingredient1", "Quantity 1")
                    ),
                    steps = listOf(
                        "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard.",
                    )
                )
            )
        }
    }
}