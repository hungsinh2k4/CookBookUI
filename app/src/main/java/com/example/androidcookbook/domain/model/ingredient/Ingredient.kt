package com.example.androidcookbook.domain.model.ingredient

import com.google.gson.annotations.Expose
import kotlinx.serialization.Serializable

@Serializable
data class Ingredient(
    @Expose val name: String,
    @Expose val quantity: String,
)
