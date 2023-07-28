package com.trietng.coffeeapp.database.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.trietng.coffeeapp.database.entity.Cart
import com.trietng.coffeeapp.database.CoffeeRepository
import com.trietng.coffeeapp.database.dao.CartExtra
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class CartViewModel(private val repository: CoffeeRepository) : ViewModel() {
    // Insert a new coffee to the coffee table
    fun insert(coffeeId: Int, shot: Int, cup_type: Int, size: Int, ice: Int, quantity: Int, paymentStatus: Int) = viewModelScope.launch {
        repository.insertCartItem(coffeeId, shot, cup_type, size, ice, quantity, paymentStatus)
    }

    // Get total price
    val getTotalPrice: LiveData<Double?> = repository.getCartTotalPrice.asLiveData()

    // Get all entries from the cart table
    val getAllCart: LiveData<List<CartExtra>> = repository.getAllCartItem.asLiveData()

    // Delete item from the cart table
    fun delete(cartId: Int) = viewModelScope.launch {
        repository.deleteCartItem(cartId)
    }

    // Delete all items from the cart table
    fun deleteAll() = viewModelScope.launch {
        repository.deleteAllCartItem()
    }

    // Find item count from the cart table
    fun findItem(coffeeId: Int, shot: Int, type: Int, size: Int, ice: Int) = runBlocking {
        repository.findCartItem(coffeeId, shot, type, size, ice)
    }

    // Update quantity from the cart table
    fun setQuantity(cartId: Int, quantity: Int) = viewModelScope.launch {
        repository.setCartItemQuantity(cartId, quantity)
    }

    // Get total quantity
    val getTotalQuantity: LiveData<Int?> = repository.getCartTotalQuantity.asLiveData()
}

class CartViewModelFactory(private val repository: CoffeeRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CartViewModel::class.java)) {
            return CartViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}