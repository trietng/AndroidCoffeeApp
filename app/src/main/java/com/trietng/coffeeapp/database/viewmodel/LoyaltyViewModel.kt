package com.trietng.coffeeapp.database.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.trietng.coffeeapp.database.entity.Loyalty
import com.trietng.coffeeapp.database.CoffeeRepository
import kotlinx.coroutines.launch

class LoyaltyViewModel(private val repository: CoffeeRepository) : ViewModel() {
    // Add a new entry to the loyalty table
    fun insert(content: String, point: Int, timeAdded: String) = viewModelScope.launch {
        repository.insertLoyaltyItem(content, point, timeAdded)
    }

    // Get 10 most recent entries from the loyalty table
    val getRecent: LiveData<List<Loyalty>> = repository.getRecentLoyaltyItem.asLiveData()

    val getSumPoint: LiveData<Int?> = repository.getSumLoyaltyPoint.asLiveData()

    // Get current sum of points from the loyalty table
    suspend fun getCurrentSumPoint(): Int? {
        return repository.getCurrentSumLoyaltyPoint()
    }
}

class LoyaltyViewModelFactory(private val repository: CoffeeRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoyaltyViewModel::class.java)) {
            return LoyaltyViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}