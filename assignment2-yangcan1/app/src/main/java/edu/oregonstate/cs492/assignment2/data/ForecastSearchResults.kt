package edu.oregonstate.cs492.assignment2.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ForecastSearchResults(
    val list: List<ForecastPeriod>
)
