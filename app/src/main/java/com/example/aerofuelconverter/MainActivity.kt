package com.example.aerofuelconverter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.aerofuelconverter.ui.view.FuelConverterScreen
import com.example.aerofuelconverter.viewmodel.FuelViewModel
import com.example.aerofuelconverter.viewmodel.FuelViewModelFactory
import com.example.aerofuelconverter.ui.theme.AeroFuelTheme

class MainActivity : ComponentActivity() {

    // ✅ Definir ViewModel como propiedad de la clase
    private val viewModel: FuelViewModel by viewModels {
        FuelViewModelFactory(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AeroFuelTheme {
                // ✅ Ahora solo pasamos el ViewModel
                FuelConverterScreen(viewModel = viewModel)
            }
        }
    }
}