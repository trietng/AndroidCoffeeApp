package com.trietng.coffeeapp.database.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.trietng.coffeeapp.database.entity.Order
import com.trietng.coffeeapp.database.CoffeeRepository
import kotlinx.coroutines.launch

class OrderViewModel(private val repository: CoffeeRepository) : ViewModel() {
    // Add methods from CoffeeRepository.kt

    // Set order status to 1 (completed)
    fun setOrderCompleted(orderId: Int) = viewModelScope.launch {
        repository.setOrderCompleted(orderId)
    }

    // Insert new order to the order table
    fun insert(content: String, totalQuantity: Int, totalPrice: Double, orderedTime: String, status: Int, address: String) = viewModelScope.launch {
        repository.insertOrderItem(content, totalQuantity, totalPrice, orderedTime, status, address)
    }

    val getCompletedOrder: LiveData<List<Order>> = repository.getCompletedOrder.asLiveData()

    val getOngoingOrder: LiveData<List<Order>> = repository.getOngoingOrder.asLiveData()

    // Get total quantity of an order
    suspend fun getTotalQuantity(orderId: Int) : Int {
        return repository.getOrderTotalQuantity(orderId)
    }
}

class OrderViewModelFactory(private val repository: CoffeeRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OrderViewModel::class.java)) {
            return OrderViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}