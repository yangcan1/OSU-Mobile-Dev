package com.example.foodrecipe.ui

import FoodSearchAdapter
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodrecipe.R
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.foodrecipe.data.Meal
import com.example.foodrecipe.data.RecipesEntity
import com.google.android.material.snackbar.Snackbar

class SavedRecipesFragment : Fragment(R.layout.fragment_recipe_saved) {
    private val recipeViewModel: SavedRecipesViewModel by viewModels()
    private val adapter: FoodSearchAdapter = FoodSearchAdapter()
    private lateinit var forecastListRV: RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        forecastListRV = view.findViewById<RecyclerView>(R.id.rv_search_results)

        forecastListRV.layoutManager = LinearLayoutManager(requireContext())
        forecastListRV.setHasFixedSize(true)

        forecastListRV.adapter = adapter

        adapter.setOnItemClickListener(object: FoodSearchAdapter.onItemClickListener{
            override fun onItemClick(position: Int, activeMeal: Meal) {

                Log.d("RecipeSearchFragment", "Clicked on item $position, contains meal:\nID: ${activeMeal.id}\nName: ${activeMeal.title}")
                val bundle = Bundle()
                bundle.putParcelable("selectedMeal", activeMeal)

                findNavController().navigate(
                    R.id.specific_recipe_view,
                    bundle
                )

            }

        })

        // Setting long click listener to remove item from database
        adapter.onItemLongClick = {
            recipeViewModel.removeSavedRecipe(
                RecipesEntity(it.id, it.title, it.image)
            )
            Snackbar.make(
                view,
                it.title + " removed to saved.",
                Snackbar.LENGTH_LONG
            ).show()
        }

        // Displays livedata from databse

        recipeViewModel.savedRecipes.observe(viewLifecycleOwner) {
            recipes ->
                val meals : MutableList<Meal> = mutableListOf()
                for (recipe in recipes) {
                    meals.add(recipe.toMeal())
                }
                adapter.updateMealList(meals)
        }

        forecastListRV.visibility = View.VISIBLE

    }
}