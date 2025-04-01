package com.example.foodrecipe.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(recipesEntity: RecipesEntity)

    @Delete
    suspend fun delete(recipesEntity: RecipesEntity)


    @Query("SELECT * FROM RecipesEntity")
    fun getAllRecipes() : Flow<List<RecipesEntity>>

    @Query("SELECT * FROM RecipesEntity WHERE id = :id LIMIT 1")
    fun getRecipeById(id: Int) : Flow<RecipesEntity?>

}