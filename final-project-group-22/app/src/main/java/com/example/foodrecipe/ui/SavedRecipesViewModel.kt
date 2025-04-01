package com.example.foodrecipe.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.room.util.query
import com.example.foodrecipe.data.AppDatabase
import com.example.foodrecipe.data.Meal
import com.example.foodrecipe.data.RecipesEntity
import com.example.foodrecipe.data.SavedRecipesRepository
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch

class SavedRecipesViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = SavedRecipesRepository(
        AppDatabase.getInstance(application).recipeDao()
    )

    val savedRecipes = repository.getAllRecipes().asLiveData()


    fun addSavedRecipe(recipesEntity: RecipesEntity) {
        viewModelScope.launch {
            repository.insertRecipe(recipesEntity)
        }
    }

    fun removeSavedRecipe(recipesEntity: RecipesEntity) {
        viewModelScope.launch {
            repository.deleteRecipe(recipesEntity)
        }
    }

    fun addOrRemoveSavedRecipe(
        recipesEntity: RecipesEntity,
        lifecycleOwner: LifecycleOwner,
        callback: (Boolean) -> Unit
    ) {
        queryRecipeById(recipesEntity.id).observe(lifecycleOwner) {
            recipe ->
                if (recipe == null) {
                    addSavedRecipe(recipesEntity)
                    callback(true)
                }
                else {
                    removeSavedRecipe(recipesEntity)
                    callback(false)
                }
        }

    }


    fun removeSavedRecipeById(id: Int, lifecycleOwner: LifecycleOwner) {
        queryRecipeById(id).observe(lifecycleOwner) {
            recipe ->
                recipe?.let {
                    removeSavedRecipe(recipe)
                }
        }
    }

    fun queryRecipeById(id: Int) =
            repository.getRecipeById(id).asLiveData()


}