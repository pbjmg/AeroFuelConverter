package com.example.aerofuelconverter.data

import androidx.room.Embedded
import androidx.room.Relation

data class FuelRecordWithDetails(
    @Embedded val fuelRecord: FuelRecord,

    @Relation(
        parentColumn = "aircraft_id",
        entityColumn = "id"
    )
    val aircraft: Aircraft?, // Puede ser null si no se ingresó una matrícula

    @Relation(
        parentColumn = "fuel_type_id",
        entityColumn = "id"
    )
    val fuelType: FuelType
)