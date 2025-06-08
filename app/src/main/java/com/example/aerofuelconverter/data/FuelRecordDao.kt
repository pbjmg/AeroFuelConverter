package com.example.aerofuelconverter.data

import androidx.room.*

@Dao
interface FuelRecordDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFuelRecord(fuelRecord: FuelRecord): Long

    @Query("SELECT * FROM fuel_records ORDER BY date DESC")
    suspend fun getAllFuelRecords(): List<FuelRecord>

    @Query("DELETE FROM fuel_records")
    suspend fun clearAllFuelRecords()

    //  Obtenemos los registros con la relación de la aeronave y el tipo de combustible
    @Transaction
    @Query("SELECT * FROM fuel_records ORDER BY date DESC")
    suspend fun getFuelRecordsWithDetails(): List<FuelRecordWithDetails>

    @Query("SELECT id FROM aircraft WHERE registration = :registration LIMIT 1")
    suspend fun getAircraftIdByRegistration(registration: String): Int?
}
