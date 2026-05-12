package com.example.nammamistri.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nammamistri.NammaMistriApplication
import com.example.nammamistri.R
import com.example.nammamistri.data.Worker

@Composable
fun LaborScreen(
    paddingValues: PaddingValues,
    viewModel: LaborViewModel = viewModel(
        factory = LaborViewModelFactory(
            (LocalContext.current.applicationContext as NammaMistriApplication).database.workerDao()
        )
    )
) {
    val workerList by viewModel.allWorkers.collectAsState(initial = emptyList())
    var showAddWorkerDialog by remember { mutableStateOf(false) }
    var showAddAdvanceDialog by remember { mutableStateOf<Worker?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .background(Color(0xFFFAFAFA)) // Very light grey background
    ) {
        // Light Header
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFEFEBE9)) // Light brown header (100)
                .padding(16.dp)
        ) {
            Text(
                text = stringResource(R.string.labor_diary_title),
                color = Color(0xFF3E2723), // Dark brown text
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = stringResource(R.string.labor_diary_subtitle),
                color = Color(0xFF3E2723).copy(alpha = 0.7f),
                fontSize = 14.sp
            )
        }

        // Top Buttons
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(
                onClick = { showAddWorkerDialog = true },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)), // Green
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(stringResource(R.string.add_worker), fontWeight = FontWeight.Bold, color = Color.White)
            }
            Button(
                onClick = { /* Implement mark day end logic if needed */ },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF9800)), // Orange
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(stringResource(R.string.mark_day_end), fontWeight = FontWeight.Bold, color = Color.White)
            }
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            items(workerList, key = { it.id }) { worker ->
                WorkerCard(
                    worker = worker,
                    onPresentClick = { viewModel.incrementDays(worker.id) },
                    onAddAdvanceClick = { showAddAdvanceDialog = worker },
                    onRemoveClick = { viewModel.deleteWorker(worker) }
                )
            }
        }
    }

    // Add Worker Dialog
    if (showAddWorkerDialog) {
        var name by remember { mutableStateOf("") }
        var role by remember { mutableStateOf("helper") }
        var wage by remember { mutableStateOf("") }

        AlertDialog(
            onDismissRequest = { showAddWorkerDialog = false },
            title = { Text("Add New Worker") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Name") })
                    OutlinedTextField(value = role, onValueChange = { role = it }, label = { Text("Role") })
                    OutlinedTextField(
                        value = wage, 
                        onValueChange = { wage = it }, 
                        label = { Text("Daily Wage") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                }
            },
            confirmButton = {
                Button(onClick = {
                    val w = wage.toDoubleOrNull() ?: 0.0
                    if (name.isNotBlank()) {
                        viewModel.addWorker(name, role, w)
                        showAddWorkerDialog = false
                    }
                }) { Text("Add") }
            }
        )
    }

    // Add Advance Dialog
    if (showAddAdvanceDialog != null) {
        var amount by remember { mutableStateOf("") }
        AlertDialog(
            onDismissRequest = { showAddAdvanceDialog = null },
            title = { Text("Add Advance for ${showAddAdvanceDialog?.name}") },
            text = {
                OutlinedTextField(
                    value = amount, 
                    onValueChange = { amount = it }, 
                    label = { Text("Amount") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            },
            confirmButton = {
                Button(onClick = {
                    val a = amount.toDoubleOrNull() ?: 0.0
                    showAddAdvanceDialog?.let { viewModel.addAdvance(it.id, a) }
                    showAddAdvanceDialog = null
                }) { Text("Save") }
            }
        )
    }
}

@Composable
fun WorkerCard(
    worker: Worker,
    onPresentClick: () -> Unit,
    onAddAdvanceClick: () -> Unit,
    onRemoveClick: () -> Unit
) {
    val earned = worker.wage * worker.days
    val balance = earned - worker.advance

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White), // White card
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(text = worker.name, color = Color(0xFF5D4037), fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    Text(text = worker.role, color = Color.Gray, fontSize = 14.sp)
                    Text(text = "₹${worker.wage.toInt()}/day", color = Color.Gray, fontSize = 14.sp)
                }
                
                Button(
                    onClick = onPresentClick,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
                    shape = RoundedCornerShape(8.dp),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                    modifier = Modifier.height(40.dp)
                ) {
                    Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(Modifier.width(4.dp))
                    Text(stringResource(R.string.present), fontSize = 14.sp)
                }
            }

            Spacer(Modifier.height(16.dp))

            // Stats Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                StatBox(label = "DAYS", value = "${worker.days}", modifier = Modifier.weight(1f))
                StatBox(label = "EARNED", value = "₹${earned.toInt()}", modifier = Modifier.weight(1f))
                StatBox(label = "ADVANCE", value = "₹${worker.advance.toInt()}", modifier = Modifier.weight(1f))
                StatBox(
                    label = "BALANCE", 
                    value = "₹${balance.toInt()}", 
                    valueColor = if (balance < 0) Color.Red else Color(0xFF388E3C),
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(Modifier.height(16.dp))

            // Bottom Actions
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedButton(
                    onClick = onAddAdvanceClick,
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFF795548))
                ) {
                    Text(stringResource(R.string.add_advance), fontSize = 14.sp)
                }
                OutlinedButton(
                    onClick = onRemoveClick,
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Red)
                ) {
                    Text(stringResource(R.string.remove), fontSize = 14.sp)
                }
            }
        }
    }
}

@Composable
fun StatBox(label: String, value: String, valueColor: Color = Color.Black, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier,
        color = Color(0xFFF5F5F5), // Light grey box
        shape = RoundedCornerShape(4.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE0E0E0))
    ) {
        Column(
            modifier = Modifier.padding(vertical = 8.dp).fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = value, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = valueColor)
            Text(text = label, fontSize = 10.sp, color = Color.Gray)
        }
    }
}
