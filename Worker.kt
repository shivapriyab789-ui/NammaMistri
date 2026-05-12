package com.example.nammamistri.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Worker(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val role: String = "helper",
    val wage: Double,
    val days: Int = 0,
    val advance: Double = 0.0
)
