package com.example.nammamistri.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkerDao {
    @Query("SELECT * FROM Worker ORDER BY id DESC")
    fun getAllWorkers(): Flow<List<Worker>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWorker(worker: Worker)

    @Query("UPDATE Worker SET days = days + 1 WHERE id = :id")
    suspend fun incrementDays(id: Int)

    @Query("UPDATE Worker SET advance = advance + :amount WHERE id = :id")
    suspend fun addAdvance(id: Int, amount: Double)

    @Delete
    suspend fun deleteWorker(worker: Worker)
}
