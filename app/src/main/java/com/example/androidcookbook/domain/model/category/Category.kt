package com.example.androidcookbook.domain.model.category

data class CategoryResponse(
    val categories: MutableList<Category>
)

data class Category(
    val idCategory: String,
    val strCategory: String,
    var strCategoryThumb: String,
    val strCategoryDescription: String,
)