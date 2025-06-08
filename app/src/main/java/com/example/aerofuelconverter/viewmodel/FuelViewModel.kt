package com.example.aerofuelconverter.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.aerofuelconverter.data.Aircraft

import com.example.aerofuelconverter.data.FuelDatabase
import com.example.aerofuelconverter.data.FuelRecord
import com.example.aerofuelconverter.data.FuelRecordWithDetails
import com.example.aerofuelconverter.data.FuelRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class FuelViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: FuelRepository

    init {
        val database = FuelDatabase.getDatabase(application)
        val fuelDao = database.fuelRecordDao()
        val aircraftDao = database.aircraftDao() //  Obtener también AircraftDao
        repository = FuelRepository(fuelDao, aircraftDao) //  Pasar ambos DAOs al repositorio
    }

    private val _fuelType = MutableStateFlow("")
    val fuelType: StateFlow<String> = _fuelType

    private val _selectedUnit = MutableStateFlow<String?>(null)
    val selectedUnit: StateFlow<String?> = _selectedUnit

    private val _currentFuel = MutableStateFlow(0.0)
    val currentFuel: StateFlow<Double> = _currentFuel

    private val _desiredFuel = MutableStateFlow(0.0)
    val desiredFuel: StateFlow<Double> = _desiredFuel

    private val _fuelToAdd = MutableStateFlow(mapOf("Kilograms" to 0.0, "Pounds" to 0.0, "Liters" to 0.0, "Gallons" to 0.0))
    val fuelToAdd: StateFlow<Map<String, Double>> = _fuelToAdd

    private val _records = MutableStateFlow<List<FuelRecordWithDetails>>(emptyList())
    val records: StateFlow<List<FuelRecordWithDetails>> = _records


    fun setFuelType(type: String) {
        _fuelType.value = type
        updateFuelToAdd()
    }

    fun setSelectedUnit(unit: String) {
        _selectedUnit.value = unit
        updateFuelToAdd()
    }

    fun setCurrentFuel(value: Double) {
        _currentFuel.value = value
        updateFuelToAdd()
    }

    fun setDesiredFuel(value: Double) {
        _desiredFuel.value = value
        updateFuelToAdd()
    }

    //  Método que calcula automáticamente fuelToAdd en función de la unidad seleccionada y el tipo de combustible
    private fun updateFuelToAdd() {
        val unit = _selectedUnit.value ?: return
        // Diferencia entre combustible deseado y actual
        val diff = _desiredFuel.value - _currentFuel.value

        //  Densidad del combustible según el tipo de combustible
        val densityKgPerL = if (_fuelType.value == "AVGAS") 0.72 else 0.804
        val densityLbsPerL = densityKgPerL * 2.20462 //  Conversión de kg a libras
        val litersPerGallon = 3.785

        // Conversión según unidad seleccionada
        _fuelToAdd.value = when (unit) {
            "Kilograms" -> mapOf(
                "Kilograms" to diff,
                "Pounds" to diff * (densityLbsPerL / densityKgPerL),
                "Liters" to diff / densityKgPerL,
                "Gallons" to (diff / densityKgPerL) * 0.264
            )
            "Pounds" -> mapOf(
                "Kilograms" to diff * (densityKgPerL / densityLbsPerL),
                "Pounds" to diff,
                "Liters" to diff / densityLbsPerL,
                "Gallons" to (diff / densityLbsPerL) * 0.264
            )
            "Gallons" -> mapOf(
                "Kilograms" to (diff * litersPerGallon) * densityKgPerL,
                "Pounds" to (diff * litersPerGallon) * densityLbsPerL,
                "Liters" to diff * litersPerGallon,
                "Gallons" to diff
            )
            else -> mapOf( //  Litros por defecto
                "Kilograms" to diff * densityKgPerL,
                "Pounds" to diff * densityLbsPerL,
                "Liters" to diff,
                "Gallons" to diff * 0.264
            )
        }
    }

    //  Función para guardar un registro en la base de datos

    fun saveRecord(aircraftRegistration: String?) {
        viewModelScope.launch {
            val dateFormat = SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault())
            val date = dateFormat.format(Date())

            val unit = _selectedUnit.value ?: return@launch
            val quantity = _fuelToAdd.value[unit] ?: return@launch

            // Obtener el ID del tipo de combustible
            val fuelTypeId = when (_fuelType.value) {
                "AVGAS" -> 1
                "JET-A1" -> 2
                else -> return@launch
            }

            // Obtener el ID de la aeronave (si existe)
            val aircraftId = withContext(Dispatchers.IO) {
                aircraftRegistration?.takeIf { it.isNotBlank() }?.let { registration ->
                    val existingId = repository.getAircraftIdByRegistration(registration)
                    Log.d("FuelViewModel", "Aircraft ID found: $existingId for registration: $registration")
                    existingId ?: repository.insertAircraft(Aircraft(registration = registration)).toInt()
                }
            }

            val record = FuelRecord(
                date = date,
                aircraft_id = aircraftId,
                fuel_type_id = fuelTypeId,
                unit = unit,
                quantity = quantity
            )

            repository.insertRecord(record)
            loadRecords() //  Cargar registros actualizados
        }
    }

    //  Función para recuperar registros de repostaje desde la base de datos
    fun loadRecords() {
        viewModelScope.launch {
            _records.value = repository.getAllRecordsWithDetails() //  Cargar los detalles completos
        }
    }

    //  Función para borrar todos los registros
    fun clearRecords() {
        viewModelScope.launch {
            repository.clearAllRecords()
            _records.value = emptyList() //  Limpiar la lista en la UI
        }
    }
}







