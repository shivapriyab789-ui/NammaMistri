package com.example.nammamistri.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.nammamistri.data.Rate
import com.example.nammamistri.data.RateDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class RatesViewModel(private val rateDao: RateDao) : ViewModel() {

    val allRates: Flow<List<Rate>> = rateDao.getAllRates()

    fun updateRate(name: String, price: Double, unit: String) {
        viewModelScope.launch {
            rateDao.insertRate(Rate(name, price, unit))
        }
    }
}

class RatesViewModelFactory(private val rateDao: RateDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RatesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RatesViewModel(rateDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
