package com.example.androidcookbook.data.repositories

import com.example.androidcookbook.data.network.CategoriesService
import javax.inject.Inject

class CategoriesRepository @Inject constructor(
    private val categoriesService: CategoriesService
) {
    suspend fun getCategories() = categoriesService.getCategories().categories
}

