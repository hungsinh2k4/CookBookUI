package com.example.androidcookbook.domain.model.aigen

import com.example.androidcookbook.domain.model.ingredient.Ingredient
import com.google.gson.annotations.Expose

data class UploadDataToAi(
    @Expose val ingredients: MutableList<Ingredient>,
    @Expose val recipes: MutableList<String>,
    @Expose val note: String
)
