package com.example.simple_arithmetic

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.simple_arithmetic.ui.theme.Simple_arithmeticTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Simple_arithmeticTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ArithmeticCalculator(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun ArithmeticCalculator(modifier: Modifier = Modifier) {
    var operand1 by remember { mutableStateOf("") }
    var operand2 by remember { mutableStateOf("") }
    var selectedOperation by remember { mutableStateOf("Addition") }
    var result by remember { mutableStateOf<String?>(null) }
    var errorMessage by remember { mutableStateOf("") }

    val operations = listOf("Addition", "Subtraction", "Multiplication", "Division")

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = operand1,
            onValueChange = { operand1 = it },
            label = { Text("Operand 1") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = operand2,
            onValueChange = { operand2 = it },
            label = { Text("Operand 2") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Radio buttons to select an operation
        Text("Select Operation")
        operations.forEach { operation ->
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = selectedOperation == operation,
                    onClick = { selectedOperation = operation }
                )
                Text(operation)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            val op1 = operand1.toDoubleOrNull()
            val op2 = operand2.toDoubleOrNull()

            if (op1 == null || op2 == null) {
                errorMessage = "Please enter valid numbers"
                result = null
            } else {
                errorMessage = ""
                result = when (selectedOperation) {
                    "Addition" -> (op1 + op2).toString()
                    "Subtraction" -> (op1 - op2).toString()
                    "Multiplication" -> (op1 * op2).toString()
                    "Division" -> if (op2 != 0.0) (op1 / op2).toString() else {
                        errorMessage = "Divide by zero not allowed"
                        null
                    }
                    else -> null
                }
            }
        }) {
            Text("Calculate")
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (errorMessage.isNotEmpty()) {
            Text(text = errorMessage, color = MaterialTheme.colorScheme.error)
        } else if (result != null) {
            Text("Result: $result")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ArithmeticCalculatorPreview() {
    Simple_arithmeticTheme {
        ArithmeticCalculator()
    }
}
