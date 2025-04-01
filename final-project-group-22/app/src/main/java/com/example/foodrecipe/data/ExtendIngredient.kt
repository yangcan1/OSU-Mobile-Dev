package com.example.foodrecipe.data

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ExtendIngredient(
    val original: String
)
