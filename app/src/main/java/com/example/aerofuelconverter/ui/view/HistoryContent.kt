package com.example.aerofuelconverter.ui.view

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.aerofuelconverter.data.FuelRecord
import com.example.aerofuelconverter.data.FuelRecordWithDetails

@Composable
fun HistoryContent(records: List<FuelRecordWithDetails>, onClearRecords: () -> Unit) {

    val context = LocalContext.current

    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Refueling History", style = MaterialTheme.typography.titleLarge)

        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn {
            items(records) { record ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = "Date: ${record.fuelRecord.date}")
                        Text(text = "Aircraft: ${record.aircraft?.registration ?: "N/A"}")
                        Text(text = "Fuel: ${record.fuelType?.name ?: "Unknown"}")
                        Text(text = "Quantity: ${record.fuelRecord.quantity} ${record.fuelRecord.unit}")
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        //  Botón para borrar historial
        Button(
            onClick = {
                onClearRecords()
                Toast.makeText(context, "History successfully deleted", Toast.LENGTH_SHORT).show()
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
        ) {
            Text(text = "Delete History", color = MaterialTheme.colorScheme.onError)
        }
    }
}