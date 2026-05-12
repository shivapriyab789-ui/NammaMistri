package com.example.nammamistri

import android.app.Application
import androidx.room.Room
import com.example.nammamistri.data.AppDatabase

class NammaMistriApplication : Application() {
    val database: AppDatabase by lazy {
        Room.databaseBuilder(
            this,
            AppDatabase::class.java,
            "namma_mistri_db"
        )
        .fallbackToDestructiveMigration() // Simplifies development when schema changes
        .build()
    }
}
