package com.example.aerofuelconverter.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "fuel_records",
    foreignKeys = [
        ForeignKey(
            entity = Aircraft::class,
            parentColumns = ["id"],
            childColumns = ["aircraft_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = FuelType::class,
            parentColumns = ["id"],
            childColumns = ["fuel_type_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["aircraft_id"]), Index(value = ["fuel_type_id"])]
)
data class FuelRecord(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val date: String,
    val aircraft_id: Int?, // Clave foránea opcional
    val fuel_type_id: Int, // Clave foránea obligatoria
    val unit: String,
    val quantity: Double
)






