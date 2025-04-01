package com.example.foodrecipe.data

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SpoonacularService {
    @GET("complexSearch")
    suspend fun searchRecipes(
        @Query("query") query: String = "pasta",
        @Query("apiKey") aipKey: String = "7eeb9dec6924484dbd18320c7316ee6c"
    ): Response<FoodSearchResult>

    @GET("{recipeID}/information")
    suspend fun getRecipe(
        @Path("recipeID") recipeID: String = "654959",
        @Query("apiKey") aipKey: String = "7eeb9dec6924484dbd18320c7316ee6c"
    ): Response<FoodRecipe>


    companion object {
        private const val BASE_URL = "https://api.spoonacular.com/recipes/"

        fun create(): SpoonacularService {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
                .create(SpoonacularService::class.java)
        }
    }
}