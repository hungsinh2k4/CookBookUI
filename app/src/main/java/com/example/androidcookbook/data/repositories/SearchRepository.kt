package com.example.androidcookbook.data.repositories

import com.example.androidcookbook.data.network.SearchService
import com.example.androidcookbook.domain.model.recipe.RecipeList
import com.skydoves.sandwich.ApiResponse
import javax.inject.Inject

class SearchRepository @Inject constructor(
    private val searchService: SearchService
) {
    suspend fun search(searchQuery: String): ApiResponse<RecipeList?> = searchService.search(searchQuery)
}
