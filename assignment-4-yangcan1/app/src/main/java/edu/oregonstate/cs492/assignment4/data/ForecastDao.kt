package edu.oregonstate.cs492.assignment4.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ForecastDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(period: DataEntities)

    @Delete
    suspend fun delete(period: DataEntities)

    @Query("SELECT * FROM DataEntities ORDER BY timestamp DESC")
    fun getAllCities(): Flow<List<DataEntities>>

    @Query("SELECT * FROM DataEntities WHERE cityName = :cityName LIMIT 1")
    fun getCityByName(cityName: String) : Flow<DataEntities?>

}