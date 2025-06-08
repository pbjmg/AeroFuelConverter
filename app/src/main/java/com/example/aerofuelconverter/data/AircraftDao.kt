package com.example.aerofuelconverter.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AircraftDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAircraft(aircraft: Aircraft): Long

    @Query("SELECT * FROM aircraft")
    suspend fun getAllAircrafts(): List<Aircraft>

    @Query("SELECT * FROM aircraft WHERE id = :id")
    suspend fun getAircraftById(id: Int): Aircraft?
}