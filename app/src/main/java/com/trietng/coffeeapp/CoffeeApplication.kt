package com.trietng.coffeeapp

import android.app.Application
import com.trietng.coffeeapp.database.CoffeeRoomDatabase
import com.trietng.coffeeapp.database.CoffeeRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class CoffeeApplication : Application() {


    val database by lazy { CoffeeRoomDatabase.getDatabase(this) }
    val repository by lazy {
        CoffeeRepository(
            database.userDao(),
            database.coffeeDao(),
            database.cartDao(),
            database.orderDao(),
            database.loyaltyDao(),
            database.voucherDao()
        )
    }
}