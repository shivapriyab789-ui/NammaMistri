package com.example.nammamistri.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nammamistri.NammaMistriApplication
import com.example.nammamistri.R
import com.example.nammamistri.data.Rate

@Composable
fun RatesScreen(
    paddingValues: PaddingValues,
    viewModel: RatesViewModel = viewModel(
        factory = RatesViewModelFactory(
            (LocalContext.current.applicationContext as NammaMistriApplication).database.rateDao()
        )
    )
) {
    var materialName by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var unit by remember { mutableStateOf("") }

    val ratesList by viewModel.allRates.collectAsState(initial = emptyList())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .background(Color(0xFFFDFCFB)) // Very light background
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(R.string.rates_tab),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF3E2723),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = materialName,
            onValueChange = { materialName = it },
            label = { Text("Material Name (ಹೆಸರು)") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White
            )
        )

        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(
                value = price,
                onValueChange = { price = it },
                label = { Text(stringResource(R.string.material_price)) },
                modifier = Modifier.weight(1f),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                )
            )
            OutlinedTextField(
                value = unit,
                onValueChange = { unit = it },
                label = { Text("Unit (eg. Bag/Load)") },
                modifier = Modifier.weight(1f),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                )
            )
        }

        Button(
            onClick = {
                val p = price.toDoubleOrNull() ?: 0.0
                if (materialName.isNotBlank()) {
                    viewModel.updateRate(materialName, p, unit)
                    materialName = ""
                    price = ""
                    unit = ""
                }
            },
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp).height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8D6E63)),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(stringResource(R.string.update_price), fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(Modifier.height(24.dp))

        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(ratesList) { rate ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp).fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(rate.materialName, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color(0xFF5D4037))
                        Text("₹${rate.price} / ${rate.unit}", fontSize = 16.sp, color = Color.Gray)
                    }
                }
            }
        }
    }
}
