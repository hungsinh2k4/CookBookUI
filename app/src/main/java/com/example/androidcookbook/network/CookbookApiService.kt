package com.example.androidcookbook.network

import com.example.androidcookbook.model.Category
import com.example.androidcookbook.model.CategoryResponse
import retrofit2.http.GET

interface CookbookApiService {
    @GET("categories.php")
    suspend fun getCategories(): CategoryResponse
}