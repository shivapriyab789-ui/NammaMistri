package com.example.nammamistri.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Rate(
    @PrimaryKey val materialName: String,
    val price: Double,
    val unit: String
)
