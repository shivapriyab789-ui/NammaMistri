package com.example.nammamistri.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.nammamistri.data.Worker
import com.example.nammamistri.data.WorkerDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class LaborViewModel(private val workerDao: WorkerDao) : ViewModel() {

    val allWorkers: Flow<List<Worker>> = workerDao.getAllWorkers()

    fun addWorker(name: String, role: String, wage: Double) {
        viewModelScope.launch {
            val worker = Worker(name = name, role = role, wage = wage)
            workerDao.insertWorker(worker)
        }
    }

    fun incrementDays(workerId: Int) {
        viewModelScope.launch {
            workerDao.incrementDays(workerId)
        }
    }

    fun addAdvance(workerId: Int, amount: Double) {
        viewModelScope.launch {
            workerDao.addAdvance(workerId, amount)
        }
    }

    fun deleteWorker(worker: Worker) {
        viewModelScope.launch {
            workerDao.deleteWorker(worker)
        }
    }
}

class LaborViewModelFactory(private val workerDao: WorkerDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LaborViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LaborViewModel(workerDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
