package edu.oregonstate.cs492.assignment4.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.io.Serializable

@Entity
@JsonClass(generateAdapter = true)
data class DataEntities(
    @PrimaryKey
    val cityName: String,
    var timestamp: Long
): Serializable
