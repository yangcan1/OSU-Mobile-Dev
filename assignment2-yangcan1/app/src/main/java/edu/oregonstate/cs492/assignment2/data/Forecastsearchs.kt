package edu.oregonstate.cs492.assignment2.data

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Forecastsearchs (private val service: ForecastService,
                       private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend fun loadRepositoriesSearch(query: String): Result<List<ForecastPeriod>> =
        withContext(ioDispatcher) {
            try {
                val response = service.searchForecasts(query)
                if (response.isSuccessful) {
                    Result.success(response.body()?.list ?: listOf())
                } else {
                    Result.failure(Exception(response.errorBody()?.string()))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }


}

