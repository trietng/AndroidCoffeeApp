package com.trietng.coffeeapp.database.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.trietng.coffeeapp.database.CoffeeRepository
import kotlinx.coroutines.launch

class VoucherViewModel(private val repository: CoffeeRepository) : ViewModel() {
    // Get all vouchers
    val getAllVoucher = repository.getAllVoucher.asLiveData()
    // Remove a voucher from the voucher table
    fun deleteVoucher(id: Int) = viewModelScope.launch {
        repository.deleteVoucher(id)
    }
}

class VoucherViewModelFactory(private val repository: CoffeeRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(VoucherViewModel::class.java)) {
            return VoucherViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}