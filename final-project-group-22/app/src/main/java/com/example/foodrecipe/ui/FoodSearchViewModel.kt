package com.example.foodrecipe.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodrecipe.data.FoodSearchRepository
import com.example.foodrecipe.data.Meal
import com.example.foodrecipe.data.SpoonacularService
import kotlinx.coroutines.launch

class FoodSearchViewModel: ViewModel() {
    private val searchs = FoodSearchRepository(SpoonacularService.create())
    private val _searchResults = MutableLiveData<List<Meal>?>(null)
    val searchResults: LiveData<List<Meal>?> = _searchResults

    fun loadSearchResults(query: String, apiKey: String) {
        viewModelScope.launch {
            val result = searchs.loadRepositoriesSearch(query, apiKey)
            _searchResults.value = result.getOrNull()
        }
    }

}