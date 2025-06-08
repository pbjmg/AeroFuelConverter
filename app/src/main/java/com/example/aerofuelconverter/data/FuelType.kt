package com.example.aerofuelconverter.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "fuel_types")
data class FuelType(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String // Nombre del tipo de combustible (AVGAS, JET-A1)
)