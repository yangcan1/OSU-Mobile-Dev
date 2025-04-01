package edu.oregonstate.cs492.assignment2.data

import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ForecastService {
    @GET("data/2.5/forecast")
    suspend fun searchForecasts(
        @Query("q") query: String = "Corvallis,OR,US",
        @Query("units") units: String = "metric",
        @Query("appid") appid: String = "8f828d7dcc772d70acc1e436ada69c5d"
    ): Response<ForecastSearchResults>

    companion object {
        private const val BASE_URL = "https://api.openweathermap.org/"

        fun create(): ForecastService {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
                .create(ForecastService::class.java)
        }
    }
}

