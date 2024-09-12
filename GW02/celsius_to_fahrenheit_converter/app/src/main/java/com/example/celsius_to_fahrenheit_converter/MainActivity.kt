package com.example.celsius_to_fahrenheit_converter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.celsius_to_fahrenheit_converter.ui.theme.Celsius_to_fahrenheit_converterTheme
import kotlin.math.roundToInt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Celsius_to_fahrenheit_converterTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    TemperatureConverter(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun TemperatureConverter(modifier: Modifier = Modifier) {
    var celsius by remember { mutableStateOf(0f) }
    var fahrenheit by remember { mutableStateOf(32f) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(100.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Celsius: ${celsius.roundToInt()}", style = MaterialTheme.typography.headlineMedium)

        // Celsius Slider (0 to 100°C)
        Slider(
            value = celsius,
            onValueChange = {
                celsius = it
                fahrenheit = celsiusToFahrenheit(it)
            },
            valueRange = 0f..100f
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(text = "Fahrenheit: ${fahrenheit.roundToInt()}", style = MaterialTheme.typography.headlineMedium)

        // Fahrenheit Slider (0 to 212°F)
        Slider(
            value = fahrenheit,
            onValueChange = {
                if (it >= 32f) {
                    fahrenheit = it
                    celsius = fahrenheitToCelsius(it)
                }
            },
            valueRange = 0f..212f
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Message based on the Celsius value
        Text(
            text = if (celsius <= 20f) "I wish it were warmer." else "I wish it were colder.",
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

// Function to convert Celsius to Fahrenheit
fun celsiusToFahrenheit(celsius: Float): Float {
    return (celsius * 9 / 5) + 32
}

// Function to convert Fahrenheit to Celsius
fun fahrenheitToCelsius(fahrenheit: Float): Float {
    return (fahrenheit - 32) * 5 / 9
}

@Preview(showBackground = true)
@Composable
fun TemperatureConverterPreview() {
    Celsius_to_fahrenheit_converterTheme {
        TemperatureConverter()
    }
}