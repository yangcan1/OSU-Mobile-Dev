package com.example.foodrecipe.data

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FoodRecipeRepository (private val service: SpoonacularService,
                            private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend fun loadRepositoriesSearchRecipe(recipeID: String, apiKey: String): Result<FoodRecipe?> =
        withContext(ioDispatcher) {
            try {
                val response = service.getRecipe(recipeID, apiKey)
                if (response.isSuccessful) {

                    Result.success(response.body())
                } else {
                    Result.failure(Exception(response.errorBody()?.string()))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
}

