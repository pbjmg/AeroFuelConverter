package com.example.aerofuelconverter.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun DropdownMenuSelector(
    label: String,
    options: List<String>,
    selectedOption: String,
    onSelect: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }


    Column(modifier = modifier.padding(8.dp)) {
        Text(text = label, fontWeight = FontWeight.Bold)

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp,Color.LightGray)
                .background(
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    shape = RoundedCornerShape(12.dp)
                    )
                .clickable { expanded = true }
                .padding(8.dp)
        ) {
            Text(text = selectedOption)
        }

        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth()) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(text = option) },
                    onClick = {
                        onSelect(option)
                        expanded = false

                    }
                )
            }
        }
    }
}