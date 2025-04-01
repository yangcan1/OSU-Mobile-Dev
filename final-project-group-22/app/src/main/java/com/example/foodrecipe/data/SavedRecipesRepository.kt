package com.example.foodrecipe.data

class SavedRecipesRepository(
    private val dao: RecipeDao
) {

    suspend fun insertRecipe(recipesEntity: RecipesEntity) = dao.insert(recipesEntity)

    suspend fun deleteRecipe(recipesEntity: RecipesEntity) = dao.delete(recipesEntity)

    fun getAllRecipes() = dao.getAllRecipes()

    fun getRecipeById(id: Int) = dao.getRecipeById(id)

}