package com.example.androidcookbook.data

import com.example.androidcookbook.model.Category
import com.example.androidcookbook.network.CookbookApiService

interface CookbookRepository {
    suspend fun getCategories(): List<Category>
}

class DefaultCookbookRepository(
    private val cookbookApiService: CookbookApiService
) : CookbookRepository {
    override suspend fun getCategories(): List<Category> = cookbookApiService.getCategories().categories
}