package com.example.aerofuelconverter.data

import androidx.room.Insert
import androidx.room.OnConflictStrategy

class FuelRepository(
    private val dao: FuelRecordDao,
    private val aircraftDao: AircraftDao
) {

    suspend fun insertRecord(record: FuelRecord) {
        dao.insertFuelRecord(record)
    }

    suspend fun clearAllRecords() {
        dao.clearAllFuelRecords()
    }

    suspend fun getAllRecordsWithDetails(): List<FuelRecordWithDetails> {
        return dao.getFuelRecordsWithDetails()
    }

    suspend fun getAircraftIdByRegistration(registration: String): Int? {
        return dao.getAircraftIdByRegistration(registration)
    }

    suspend fun insertAircraft(aircraft: Aircraft): Long {
        return aircraftDao.insertAircraft(aircraft)
    }

}
