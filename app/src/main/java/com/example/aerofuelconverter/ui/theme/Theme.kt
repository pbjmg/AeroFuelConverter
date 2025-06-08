package com.example.aerofuelconverter.ui.theme

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColors = lightColorScheme(
    primary = PurpleDark, // Morado Oscuro (Encabezado)
    primaryContainer = PurplePrimary, // Morado Vivo
    secondary = PurpleLight, // Morado Pastel (Fondo de las Cards)
    secondaryContainer = PurpleLight, // Fondo de las Cards
    background = Color.White, // Fondo blanco
    surface = PurpleLight, // Fondo de Cards
    surfaceVariant = Color(0xFFD1C4E9),
    outline = GrayLight, // Bordes de los inputs
    onPrimary = Color.White, // Texto en elementos primarios
    onSecondary = TextBlack, // Texto en Cards
    onBackground = TextBlack,
    onSurface = TextBlack
)

@Composable
fun AeroFuelTheme(
    darkTheme: Boolean = false, // Modo claro por defecto
    content: @Composable () -> Unit
) {
    val colors = LightColors

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        content = content
    )
}