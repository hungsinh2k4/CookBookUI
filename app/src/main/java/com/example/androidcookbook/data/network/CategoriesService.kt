package com.example.androidcookbook.data.network

import com.example.androidcookbook.domain.model.category.CategoryResponse
import retrofit2.http.GET

interface CategoriesService {
    @GET("categories.php")
    suspend fun getCategories(): CategoryResponse
}