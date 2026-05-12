package com.example.nammamistri.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Photo(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val uri: String,
    val label: String? = null,
    val timestamp: Long = System.currentTimeMillis()
)
