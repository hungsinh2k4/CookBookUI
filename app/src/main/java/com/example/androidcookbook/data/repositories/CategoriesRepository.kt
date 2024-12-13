package com.example.androidcookbook.data.repositories

import com.example.androidcookbook.data.network.CategoriesService
import com.example.androidcookbook.domain.model.category.Category
import javax.inject.Inject

class CategoriesRepository @Inject constructor(
    private val categoriesService: CategoriesService
) {
    suspend fun getCategories() = categoriesService.getCategories().categories

    suspend fun getRandomMeal() = categoriesService.getRandomMeal()

    suspend fun getMealByCategories(category: String) = categoriesService.getMealByCategories(category)

    suspend fun getMealById(id: Int) = categoriesService.getMealById(id)
}

