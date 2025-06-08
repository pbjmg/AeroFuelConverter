package com.example.aerofuelconverter.ui.view

import android.app.Application
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.example.aerofuelconverter.ui.components.DropdownMenuSelector
import com.example.aerofuelconverter.viewmodel.FuelViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.aerofuelconverter.R


@OptIn(ExperimentalMaterial3Api::class) // Habilita la API experimental (rememberModalBottomSheetState())
@Composable
fun FuelConverterScreen(viewModel: FuelViewModel) {
    val fuelType by viewModel.fuelType.collectAsStateWithLifecycle()
    val selectedUnit by viewModel.selectedUnit.collectAsStateWithLifecycle()
    val fuelToAdd by viewModel.fuelToAdd.collectAsStateWithLifecycle()
    var aircraftRegistration by remember { mutableStateOf("") }

    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }

    val records by viewModel.records.collectAsState()

    var currentFuelInput by remember { mutableStateOf("") }
    var desiredFuelInput by remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(12.dp)) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primary)
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(R.string.aero_fuel_converter),
                color = MaterialTheme.colorScheme.onPrimary,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Sección de Unidades
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(8.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "SETTINGS",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                    OutlinedTextField(
                        value = aircraftRegistration,
                        onValueChange = { aircraftRegistration = it },
                        label = { Text("Aircraft") },
                        modifier = Modifier.width(120.dp).height(60.dp),
                        singleLine = true
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    DropdownMenuSelector(
                        label = "Fuel",
                        options = listOf("AVGAS", "JET-A1"),
                        selectedOption = fuelType,
                        onSelect = { viewModel.setFuelType(it) },
                        modifier = Modifier.weight(1f)

                    )

                    DropdownMenuSelector(
                        label = "Units",
                        options = listOf("Liters", "Kilograms", "Pounds", "Gallons"),
                        selectedOption = selectedUnit ?: "",
                        onSelect = { viewModel.setSelectedUnit(it) },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Sección de entrada de combustible
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(8.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                Text(text = "FUEL INPUT", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSecondaryContainer)

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(text = "Current Fuel", modifier = Modifier.padding(bottom = 8.dp))
                        OutlinedTextField(
                            value = currentFuelInput,
                            onValueChange = {
                                currentFuelInput = it
                                viewModel.setCurrentFuel(it.toDoubleOrNull() ?: 0.0)
                            },
                            modifier = Modifier.width(100.dp),
                            colors = TextFieldDefaults.colors(
                                focusedIndicatorColor = MaterialTheme.colorScheme.primary, // Morado
                                unfocusedIndicatorColor = MaterialTheme.colorScheme.outline // Gris claro
                            )
                        )
                    }

                    Column {
                        Text(text = "Desired Fuel", color = Color(0xFFFFA000), modifier = Modifier.padding(bottom = 8.dp))
                        OutlinedTextField(
                            value = desiredFuelInput,
                            onValueChange = {
                                desiredFuelInput = it
                                viewModel.setDesiredFuel(it.toDoubleOrNull() ?: 0.0)
                            },
                            modifier = Modifier.width(100.dp),
                            colors = TextFieldDefaults.colors(
                                focusedIndicatorColor = MaterialTheme.colorScheme.primary, // Morado
                                unfocusedIndicatorColor = MaterialTheme.colorScheme.outline // Gris claro

                            )
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Sección de conversión de combustible
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(8.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                Text(text = "ADD", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSecondaryContainer)

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.weight(1f)) {
                        Text(text = String.format("%.2f", fuelToAdd["Kilograms"]), fontSize = 24.sp, fontWeight = FontWeight.Bold)
                        Text(text = "Kilograms", fontSize = 14.sp)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = String.format("%.2f", fuelToAdd["Liters"]), fontSize = 24.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 16.dp))
                        Text(text = "Liters", fontSize = 14.sp)
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.weight(1f)) {
                        Text(text = String.format("%.2f", fuelToAdd["Pounds"]), fontSize = 24.sp, fontWeight = FontWeight.Bold)
                        Text(text = "Pounds", fontSize = 14.sp)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = String.format("%.2f", fuelToAdd["Gallons"]), fontSize = 24.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 16.dp))
                        Text(text = "Gallons", fontSize = 14.sp)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        //  Botón para guardar el repostaje
        Button(
            onClick = {
                viewModel.saveRecord(aircraftRegistration)
                Toast.makeText(context, "Refueling saved successfully", Toast.LENGTH_SHORT).show()
                      },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "SAVE REFUELING")
        }

        Spacer(modifier = Modifier.height(6.dp))

        //  Botón para abrir el bottomSheet con el historial
        Button(
            onClick = {
                showBottomSheet = true
                viewModel.loadRecords() //  Cargar registros antes de mostrar el historial
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "VIEW REFUELING HISTORY")
        }
    }

    //  BottomSheet para mostrar el historial
    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { showBottomSheet = false },
            sheetState = sheetState
        ) {
            HistoryContent(records, onClearRecords = { viewModel.clearRecords() })
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewFuelConverterScreen() {
    //  Crear un viewModel sin requerir un application (para evitar errores en preview)
    val fakeViewModel = FuelViewModel(Application())

    FuelConverterScreen(viewModel = fakeViewModel)
}