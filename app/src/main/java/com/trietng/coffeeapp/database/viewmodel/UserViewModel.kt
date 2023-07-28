package com.trietng.coffeeapp.database.viewmodel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.trietng.coffeeapp.database.CoffeeRepository
import com.trietng.coffeeapp.database.entity.User
import kotlinx.coroutines.launch

class UserViewModel(private val repository: CoffeeRepository) : ViewModel() {

    // Get user
    val getUser: LiveData<User> = repository.getUser.asLiveData()

    // Get user fullname

    val getFullname: LiveData<String> = repository.getUserFullname.asLiveData()

    // Get current number of loyalty cup
    suspend fun getCurrentNumLoyaltyCup(): Int {
        return repository.getCurrentNumLoyaltyCup()
    }

    suspend fun getAddress(): String {
        return repository.getUserAddress()
    }

    // Increase number of loyalty cup by 1
    fun updateNumLoyaltyCup(numLoyaltyCup: Int) = viewModelScope.launch {
        repository.updateNumLoyaltyCup(numLoyaltyCup)
    }

    // Reset number of loyalty cup to 0
    fun resetNumLoyaltyCup() = viewModelScope.launch {
        repository.resetNumLoyaltyCup()
    }

    // Update full name
    fun updateFullname(fullname: String) = viewModelScope.launch {
        repository.updateUserFullname(fullname)
    }

    // Update phone
    fun updatePhone(phone: String) = viewModelScope.launch {
        repository.updateUserPhone(phone)
    }

    // Update address
    fun updateAddress(address: String) = viewModelScope.launch {
        repository.updateUserAddress(address)
    }

    // Update email
    fun updateEmail(email: String) = viewModelScope.launch {
        repository.updateUserEmail(email)
    }
}

class UserViewModelFactory(private val repository: CoffeeRepository) : androidx.lifecycle.ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            return UserViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}