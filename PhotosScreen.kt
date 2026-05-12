package com.example.nammamistri.ui

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.nammamistri.NammaMistriApplication
import com.example.nammamistri.R
import com.example.nammamistri.data.Photo
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun PhotosScreen(
    paddingValues: PaddingValues,
    viewModel: PhotosViewModel = viewModel(
        factory = PhotosViewModelFactory(
            (LocalContext.current.applicationContext as NammaMistriApplication).database.photoDao()
        )
    )
) {
    val photos by viewModel.allPhotos.collectAsState(initial = emptyList())
    var showLabelDialog by remember { mutableStateOf(false) }
    var selectedUri by remember { mutableStateOf<Uri?>(null) }
    var tempLabel by remember { mutableStateOf("") }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            selectedUri = it
            showLabelDialog = true
        }
    }

    if (showLabelDialog) {
        AlertDialog(
            onDismissRequest = { showLabelDialog = false },
            title = { Text("Add Photo Details", color = Color(0xFF3E2723)) },
            text = {
                OutlinedTextField(
                    value = tempLabel,
                    onValueChange = { tempLabel = it },
                    label = { Text("Description (eg. Site Name)") },
                    modifier = Modifier.fillMaxWidth()
                )
            },
            confirmButton = {
                Button(onClick = {
                    selectedUri?.let { viewModel.addPhoto(it.toString(), tempLabel) }
                    showLabelDialog = false
                    tempLabel = ""
                }, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8D6E63))) {
                    Text("Save")
                }
            },
            dismissButton = {
                TextButton(onClick = { showLabelDialog = false }) {
                    Text("Cancel", color = Color.Gray)
                }
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .background(Color(0xFFF9F7F5)) // Light Background
    ) {
        // Light Header
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFE0E0E0)) // Light grey header
                .padding(16.dp)
        ) {
            Text(
                text = stringResource(R.string.photos_tab),
                color = Color(0xFF3E2723),
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Box(modifier = Modifier.fillMaxSize()) {
            if (photos.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("No photos added yet.", color = Color.Gray)
                }
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(photos, key = { it.id }) { photo ->
                        PhotoItem(photo = photo, onDelete = { viewModel.deletePhoto(photo) })
                    }
                }
            }

            FloatingActionButton(
                onClick = { launcher.launch("image/*") },
                containerColor = Color(0xFF8D6E63),
                contentColor = Color.White,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(24.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Photo")
            }
        }
    }
}

@Composable
fun PhotoItem(photo: Photo, onDelete: () -> Unit) {
    val dateStr = remember(photo.timestamp) {
        SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(Date(photo.timestamp))
    }
    val timeStr = remember(photo.timestamp) {
        SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date(photo.timestamp))
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(0.8f),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Box {
            AsyncImage(
                model = photo.uri,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            
            IconButton(
                onClick = onDelete,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(4.dp)
                    .size(28.dp)
                    .background(Color.White.copy(alpha = 0.7f), shape = RoundedCornerShape(4.dp))
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete",
                    tint = Color.Red,
                    modifier = Modifier.size(18.dp)
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .background(Color.White.copy(alpha = 0.85f))
                    .padding(4.dp)
            ) {
                if (!photo.label.isNullOrBlank()) {
                    Text(
                        text = photo.label,
                        color = Color(0xFF5D4037),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = dateStr, color = Color.Gray, fontSize = 10.sp)
                    Text(text = timeStr, color = Color.Gray, fontSize = 10.sp)
                }
            }
        }
    }
}
