package com.example.nammamistri.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nammamistri.R
import java.util.Locale

@Composable
fun CalculatorScreen(paddingValues: PaddingValues) {
    var length by remember { mutableStateOf("") }
    var height by remember { mutableStateOf("") }
    var thickness by remember { mutableStateOf("0.23") }
    
    var bricksCount by remember { mutableStateOf(0.0) }
    var cementCount by remember { mutableStateOf(0.0) }
    var sandCount by remember { mutableStateOf(0.0) }
    var hasCalculated by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .background(Color(0xFFF5F5F5)) // Light Grey Background
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(R.string.calc_tab),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF3E2723), // Dark Brown Text
            modifier = Modifier.padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = length,
            onValueChange = { length = it },
            label = { Text(stringResource(R.string.length)) },
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White
            )
        )

        OutlinedTextField(
            value = height,
            onValueChange = { height = it },
            label = { Text(stringResource(R.string.height)) },
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White
            )
        )

        OutlinedTextField(
            value = thickness,
            onValueChange = { thickness = it },
            label = { Text(stringResource(R.string.thickness)) },
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White
            )
        )

        Button(
            onClick = {
                val l = length.toDoubleOrNull() ?: 0.0
                val h = height.toDoubleOrNull() ?: 0.0
                val t = thickness.toDoubleOrNull() ?: 0.0
                val volume = l * h * t
                bricksCount = volume * 500
                cementCount = volume * 1.2 
                sandCount = volume * 0.25
                hasCalculated = true
            },
            modifier = Modifier.fillMaxWidth().height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8D6E63)), // Warm Brown
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(stringResource(R.string.calculate), fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }

        if (hasCalculated) {
            Card(
                modifier = Modifier.padding(top = 24.dp).fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(Modifier.padding(20.dp)) {
                    Text(
                        "Calculation Results", 
                        fontWeight = FontWeight.Bold, 
                        color = Color(0xFF5D4037),
                        modifier = Modifier.padding(bottom = 12.dp)
                    )
                    ResultRow(stringResource(R.string.bricks), "${bricksCount.toInt()}")
                    Divider(color = Color(0xFFEEEEEE), thickness = 1.dp, modifier = Modifier.padding(vertical = 8.dp))
                    ResultRow(stringResource(R.string.cement), String.format(Locale.getDefault(), "%.1f Bags", cementCount))
                    Divider(color = Color(0xFFEEEEEE), thickness = 1.dp, modifier = Modifier.padding(vertical = 8.dp))
                    ResultRow(stringResource(R.string.sand), String.format(Locale.getDefault(), "%.2f Units/Load", sandCount))
                }
            }
        }
    }
}

@Composable
fun ResultRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, fontSize = 16.sp, color = Color.Gray)
        Text(text = value, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.Black)
    }
}
