package edu.oregonstate.cs492.assignment2.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ForecastPeriod(
    @Json(name = "dt_txt") val dateTime: String,
    @Json(name = "main") val main: Main,
    @Json(name = "pop") val pop: Double,
    @Json(name = "weather") val weather: List<Weather>
)
@JsonClass(generateAdapter = true)
data class Main(
    @Json(name = "temp_max") val highTemp: Double,
    @Json(name = "temp_min") val lowTemp: Double
)
@JsonClass(generateAdapter = true)
data class Weather(
    @Json(name = "main") val shortDesc: String,
    @Json(name = "description") val longDesc: String
)