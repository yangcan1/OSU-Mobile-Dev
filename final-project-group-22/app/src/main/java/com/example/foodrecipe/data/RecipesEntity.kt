package com.example.foodrecipe.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RecipesEntity(
    @PrimaryKey
    val id: Int,
    val title: String,
    val image: String,

    // OTHERS FILL THEM IN FROM EXTENDED RECIPE INFO?


    // potential items
//    val summary: String,
//    val sourceUrl: String,
//    val ingredients: String,

) {
    fun toMeal(): Meal {
        return Meal(this.id, this.title, this.image)
    }
}


