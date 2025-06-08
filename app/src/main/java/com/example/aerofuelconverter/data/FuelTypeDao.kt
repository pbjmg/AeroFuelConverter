package com.example.aerofuelconverter.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FuelTypeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFuelType(fuelType: FuelType): Long

    @Query("SELECT * FROM fuel_types")
    suspend fun getAllFuelTypes(): List<FuelType>

    @Query("SELECT * FROM fuel_types WHERE id = :id")
    suspend fun getFuelTypeById(id: Int): FuelType?
}