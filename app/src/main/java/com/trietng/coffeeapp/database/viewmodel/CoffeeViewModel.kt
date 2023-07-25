package com.trietng.coffeeapp.database.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.trietng.coffeeapp.database.CoffeeRepository
import com.trietng.coffeeapp.database.entity.Coffee
import kotlinx.coroutines.runBlocking

class CoffeeViewModel(private val repository: CoffeeRepository) : ViewModel() {
    // Get all coffee
    val getAllCoffee : LiveData<List<Coffee>> = repository.getAllCoffeeItem.asLiveData()
}

class CoffeeViewModelFactory(private val repository: CoffeeRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CoffeeViewModel::class.java)) {
            return CoffeeViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}