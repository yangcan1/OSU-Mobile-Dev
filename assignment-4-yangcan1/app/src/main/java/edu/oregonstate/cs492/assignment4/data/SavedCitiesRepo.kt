package edu.oregonstate.cs492.assignment4.data

class SavedCitiesRepo (
    private val dao: ForecastDao
    ) {
        suspend fun insertCityHistory(repo: DataEntities) = dao.insert(repo)
        suspend fun deleteCityHistory(repo: DataEntities) = dao.delete(repo)
        fun getAllCityHistory() = dao.getAllCities()
        fun getForecastByName(name: String) = dao.getCityByName(name)
}