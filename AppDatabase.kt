package com.example.nammamistri.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Worker::class, Rate::class, Photo::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun workerDao(): WorkerDao
    abstract fun rateDao(): RateDao
    abstract fun photoDao(): PhotoDao
}
