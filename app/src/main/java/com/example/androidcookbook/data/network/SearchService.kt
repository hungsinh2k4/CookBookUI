package com.example.androidcookbook.data.network

import com.example.androidcookbook.domain.model.recipe.RecipeDetailsList
import com.example.androidcookbook.domain.model.recipe.RecipeList
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchService {
    @GET("search.php")
    suspend fun search(@Query("s") searchQuery: String) : ApiResponse<RecipeList?>

    @GET("lookup.php")
    suspend fun getMealById(@Query("i") id: Int): RecipeDetailsList
}
