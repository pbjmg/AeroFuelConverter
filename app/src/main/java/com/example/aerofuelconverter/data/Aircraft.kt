package com.example.aerofuelconverter.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "aircraft")
data class Aircraft(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val registration: String // Matrícula de la aeronave
)