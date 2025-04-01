package com.example.foodrecipe.data

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FoodSearchRepository (private val service: SpoonacularService,
                            private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend fun loadRepositoriesSearch(query: String, apiKey: String): Result<List<Meal>> =
        withContext(ioDispatcher) {
            try {
                val response = service.searchRecipes(query, apiKey)
                if (response.isSuccessful) {
                    Result.success(response.body()?.results?: listOf())
                } else {
                    Result.failure(Exception(response.errorBody()?.string()))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
}

