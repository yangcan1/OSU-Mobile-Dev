package com.example.foodrecipe.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodrecipe.data.FoodRecipe
import com.example.foodrecipe.data.FoodRecipeRepository
import com.example.foodrecipe.data.FoodSearchRepository
import com.example.foodrecipe.data.Meal
import com.example.foodrecipe.data.SpoonacularService
import kotlinx.coroutines.launch

class RecipeSearchViewModel: ViewModel() {
    private val searchs = FoodRecipeRepository(SpoonacularService.create())
    private val _searchResults = MutableLiveData<FoodRecipe?>(null)
    val searchResults: LiveData<FoodRecipe?> = _searchResults

    fun loadRecipeSearchResults(recipeID: String, apiKey: String) {
        viewModelScope.launch {
            val result = searchs.loadRepositoriesSearchRecipe(recipeID, apiKey)
            _searchResults.value = result.getOrNull()
            Log.d("RecipeSearchViewModel", "Search Results:\n${result}")

        }
    }

}