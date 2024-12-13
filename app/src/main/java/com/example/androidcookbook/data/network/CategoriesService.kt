package com.example.androidcookbook.data.network

import com.example.androidcookbook.domain.model.category.CategoryResponse
import com.example.androidcookbook.domain.model.recipe.RecipeDetails
import com.example.androidcookbook.domain.model.recipe.RecipeDetailsList
import com.example.androidcookbook.domain.model.recipe.RecipeList
import retrofit2.http.GET
import retrofit2.http.Query

interface CategoriesService {
    @GET("categories.php")
    suspend fun getCategories(): CategoryResponse

    @GET("random.php")
    suspend fun getRandomMeal(): RecipeList

    @GET("filter.php")
    suspend fun getMealByCategories(@Query("c") category: String): RecipeList

    @GET("lookup.php")
    suspend fun getMealById(@Query("i") id: Int): RecipeDetailsList
}