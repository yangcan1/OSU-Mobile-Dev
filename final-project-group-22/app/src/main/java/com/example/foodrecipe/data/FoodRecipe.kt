package com.example.foodrecipe.data

import com.squareup.moshi.JsonClass
import java.io.Serializable

@JsonClass(generateAdapter = true)
data class FoodRecipe(
    val extendedIngredients: List<ExtendIngredient>,
    val summary: String,
    val image: String,
    val title: String,
    val readyInMinutes: Int,
    val servings: Int,
    val instructions: String,
    val creditsText: String,
    val sourceUrl: String
) : Serializable
