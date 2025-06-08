package com.example.aerofuelconverter.data

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import java.util.concurrent.Executors

@Database(
    entities = [Aircraft::class, FuelType::class, FuelRecord::class], // Incluye todas las entidades
    version = 3,
    exportSchema = false
)
abstract class FuelDatabase : RoomDatabase() {
    abstract fun fuelRecordDao(): FuelRecordDao
    abstract fun aircraftDao(): AircraftDao
    abstract fun fuelTypeDao(): FuelTypeDao

    companion object {
        @Volatile
        private var INSTANCE: FuelDatabase? = null

        fun getDatabase(context: Context): FuelDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FuelDatabase::class.java,
                    "fuel_database"
                )
                    .addCallback(DatabaseCallback())  // Agregamos el callback aquí
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
    private class DatabaseCallback : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            // Insertamos datos iniciales en fuel_types
            db.execSQL("INSERT INTO fuel_types (name) VALUES ('AVGAS 100LL'), ('JET-A1');")
        }
    }


}
