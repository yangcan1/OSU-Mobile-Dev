package edu.oregonstate.cs492.assignment4.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import edu.oregonstate.cs492.assignment4.data.AppDatabase
import edu.oregonstate.cs492.assignment4.data.DataEntities
import edu.oregonstate.cs492.assignment4.data.SavedCitiesRepo
import kotlinx.coroutines.launch

class SavedCityViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = SavedCitiesRepo(
        AppDatabase.getInstance(application).forecastDao()
    )

    val savedCities = repository.getAllCityHistory().asLiveData()

    fun addSavedCity(repo: DataEntities) {
        viewModelScope.launch {
            repository.insertCityHistory(repo)
        }
    }


}